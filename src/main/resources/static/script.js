// listeners
document.getElementById("search-btn").addEventListener("click", fetchWeather);
document.getElementById("city-input").addEventListener("keypress", (e) => {
    if (e.key === "Enter") fetchWeather();
});

async function fetchWeather() {
    const city = document.getElementById("city-input").value.trim();
    if (!city) return;

    // oculta resultados antigos
    document.getElementById("weather-result").style.display = "none";
    document.getElementById("forecast").style.display = "none";

    try {
        const res = await fetch(`/api/weather?city=${encodeURIComponent(city)}`);
        if (!res.ok) {
            alert("Cidade não encontrada ou erro na API.");
            return;
        }

        const data = await res.json();
        if (!data || !data.current) {
            alert("Resposta inválida da API.");
            return;
        }

        // PREENCHE BLOCO ATUAL (usando nomes do DTO)
        const current = data.current;
        const cityName = current.cityName ?? current.city ?? "—";
        const temp = (typeof current.temp === "number") ? Math.round(current.temp) : "—";
        const feelsLike = (typeof current.feelsLike === "number") ? Math.round(current.feelsLike) : "—";
        const humidity = current.humidity ?? "—";
        const description = (current.description ?? "").toString();
        const currentDate = current.date ?? (current.dt ? formatTimestampToDate(current.dt) : "");

        document.getElementById("city-name").textContent = cityName;
        document.getElementById("temperature").textContent = `${temp}°C`;
        document.getElementById("description").textContent = description;
        document.getElementById("details").textContent =
            `${currentDate ? currentDate + " • " : ""}Sensação: ${feelsLike}°C • Umidade: ${humidity}%`;
        document.getElementById("weather-icon").src = getWeatherIconUrl(description);
        document.getElementById("weather-icon").alt = description || "ícone";
        document.getElementById("weather-result").style.display = "block";

        // PREVISÃO DIÁRIA
        const forecastContainer = document.getElementById("forecast-container");
        forecastContainer.innerHTML = "";

        const daily = Array.isArray(data.daily) ? data.daily : [];

        daily.forEach(item => {
            // possíveis campos que o backend pode retornar:
            // preferimos item.date; se não tiver, usamos item.dt (timestamp em segs)
            const dayOfWeek = item.dayOfWeek ?? item.day ?? "—";
            const dateFromDto = item.date ?? "";
            const dateFromDt = (item.dt ? formatTimestampToDate(item.dt) : "");
            const dateToShow = dateFromDto || dateFromDt || "";

            // temperaturas (algumas APIs usam minTemp/maxTemp ou min/max)
            const max = (typeof item.maxTemp === "number") ? Math.round(item.maxTemp)
                : (typeof item.max === "number") ? Math.round(item.max) : "—";
            const min = (typeof item.minTemp === "number") ? Math.round(item.minTemp)
                : (typeof item.min === "number") ? Math.round(item.min) : "—";

            const desc = (item.description ?? "").toString();

            const card = document.createElement("div");
            card.className = "forecast-card";
            card.innerHTML = `
                <h4>${escapeHtml(dayOfWeek)}</h4>
                <img src="${getWeatherIconUrl(desc)}" alt="${escapeHtml(desc)}">
                <p class="temps">${max}° / ${min}°C</p>
                <p class="desc">${escapeHtml(desc)}</p>
                <p class="small-date">${escapeHtml(dateToShow)}</p>
            `;
            forecastContainer.appendChild(card);
        });

        document.getElementById("forecast").style.display = daily.length ? "block" : "none";

    } catch (err) {
        console.error(err);
        alert("Erro ao buscar dados do clima.");
    }
}

/**
 * Formata timestamp (segundos) para dd/MM/yyyy
 * Aceita number (ex: 1699612800) ou string numérica.
 */
function formatTimestampToDate(ts) {
    if (!ts) return "";
    // se timestamp vier em milissegundos (13 dígitos), converte para segundos
    const t = String(ts).length > 11 ? Math.floor(Number(ts) / 1000) : Number(ts);
    const d = new Date(t * 1000);
    const day = String(d.getDate()).padStart(2, "0");
    const month = String(d.getMonth() + 1).padStart(2, "0");
    const year = d.getFullYear();
    return `${day}/${month}/${year}`;
}

/**
 * Retorna URL do ícone baseado na descrição (defensivo).
 * Ajuste URLs conforme preferir (SVGs locais, etc).
 */
function getWeatherIconUrl(description) {
    if (!description) return fallbackIcon();

    const d = description.toLowerCase();

    if (d.includes("chuva") || d.includes("tempestade") || d.includes("garoa")) {
        return "https://cdn-icons-png.flaticon.com/512/1163/1163657.png";
    }
    if (d.includes("nuvem") || d.includes("nublado") || d.includes("nuvens") || d.includes("nublado")) {
        return "https://cdn-icons-png.flaticon.com/512/1146/1146869.png";
    }
    if (d.includes("céu limpo") || d.includes("limpo") || d.includes("ensolarado") || d.includes("sol")) {
        return "https://cdn-icons-png.flaticon.com/512/869/869869.png";
    }
    if (d.includes("névoa") || d.includes("neblina") || d.includes("nevoeiro") || d.includes("mist")) {
        return "https://cdn-icons-png.flaticon.com/512/4005/4005901.png";
    }
    if (d.includes("neve") || d.includes("granizo")) {
        return "https://cdn-icons-png.flaticon.com/512/642/642102.png";
    }
    return fallbackIcon();
}

function fallbackIcon() {
    return "https://cdn-icons-png.flaticon.com/512/1163/1163624.png";
}

function escapeHtml(str) {
    if (!str) return "";
    return String(str)
        .replaceAll("&", "&amp;")
        .replaceAll("<", "&lt;")
        .replaceAll(">", "&gt;")
        .replaceAll('"', "&quot;")
        .replaceAll("'", "&#039;");
}
