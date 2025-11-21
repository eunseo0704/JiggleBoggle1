const chatBox = document.getElementById('chat-box');
const input = document.getElementById('user-input');
const sendBtn = document.getElementById('send-btn');
const recipesDiv = document.getElementById('recipes');

async function loadHistory() {
    const res = await fetch(`/api/chat/history?userId=${userId}`);
    const list = await res.json();
    list.forEach(m => addMessage(m.content, m.role === 'user' ? 'user' : 'bot'));
}

function addMessage(text, type) {
    const el = document.createElement('div');
    el.className = `message ${type}`;
    el.textContent = text;
    chatBox.appendChild(el);
    chatBox.scrollTop = chatBox.scrollHeight;
    return el;
}

sendBtn.addEventListener('click', async () => {
    const text = input.value.trim();
    if (!text) return;
    input.value = '';

    // save user message
    const saveResp = await fetch('/api/chat/send', {
        method: 'POST',
        headers: {'Content-Type':'application/json'},
        body: JSON.stringify({ userId, message: text })
    });
    await saveResp.json();

    addMessage(text, 'user');

    // prepare bot bubble
    const botEl = addMessage('', 'bot');

    // open SSE for streaming assistant reply
    const es = new EventSource(`/api/chat/stream?userId=${userId}`);
    let acc = '';

    es.addEventListener('message', (ev) => {
        const chunk = ev.data;
        acc += chunk;
        botEl.textContent = acc;
        chatBox.scrollTop = chatBox.scrollHeight;
    });

    es.addEventListener('end', (ev) => {
        es.close();
        // try to parse recipes from assistant text (if JSON array exists)
        try {
            // naive extraction: if assistant returned a JSON array, parse it
            const jsonStart = acc.indexOf('[');
            if (jsonStart !== -1) {
                const maybe = acc.substring(jsonStart);
                const parsed = JSON.parse(maybe);
                if (Array.isArray(parsed)) showRecipes(parsed);
            }
        } catch (e) {
            // ignore parse errors
        }
    });

    es.addEventListener('error', (ev) => {
        console.error('SSE error', ev);
        es.close();
    });
});

function showRecipes(list) {
    recipesDiv.innerHTML = '';
    list.forEach(r => {
        const card = document.createElement('div');
        card.className = 'recipe-card';
        const img = document.createElement('img');
        img.src = r.imageUrl || 'https://via.placeholder.com/300x200?text=no+image';
        const title = document.createElement('h4');
        title.textContent = r.title || '제목 없음';
        const desc = document.createElement('p');
        desc.textContent = r.description || '';
        card.appendChild(img);
        card.appendChild(title);
        card.appendChild(desc);
        recipesDiv.appendChild(card);
    });
}

// 초기 로드
loadHistory();
