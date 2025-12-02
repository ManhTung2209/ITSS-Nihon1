const BACKEND_URL = 'http://localhost:8080';

function displayShops(list, elementId) {
    const container = document.getElementById(elementId);
    if (!container) return;

    container.innerHTML = "";

    if (!list || list.length === 0) {
        container.innerHTML = "<p>該当するカフェがありません。</p>";
        return;
    }

    list.forEach(shop => {
        const div = document.createElement("div");
        div.className = "shop-card";

        const ratingStars = '⭐'.repeat(Math.floor(shop.rating || 0));

        const imagePath = shop.image && shop.image.startsWith('/') ? shop.image : '/images/placeholder.png';
        const imgSrc = `${BACKEND_URL}${imagePath}`;

        const description = shop.description ?? "快適な空間";

        div.innerHTML = `
            <div class="shop-image">
                <img src="${imgSrc}" alt="${shop.name}">
            </div>
            <div class="shop-info">
                <h3>${shop.name}</h3>
                <p class="shop-rating">
                    ${ratingStars} <span>(${shop.rating ?? "N/A"})</span>
                </p>
                <p class="shop-distance">
                    <i class="fas fa-map-marker-alt"></i> ${shop.distance ? shop.distance.toFixed(1) + ' km' : 'N/A'}
                </p>
                <p class="shop-description">${description}</p>
                <a href="shop-details.html?id=${shop.id ?? 0}" class="details-button">Chi tiết</a>
            </div>
        `;

        container.appendChild(div);
    });
}

function updateSearchResults(results) {
    const featuredSection = document.querySelector('.featured-section');
    const nearbySection = document.querySelector('.nearby-section');
    const nearbyTitle = nearbySection ? nearbySection.querySelector('h2') : null;

    if (featuredSection) featuredSection.style.display = 'none';

    if (nearbyTitle) nearbyTitle.innerHTML = '🔍 検索結果';

    displayShops(results, "nearbyShops");
}

function resetUI() {
    const featuredSection = document.querySelector('.featured-section');
    const nearbySection = document.querySelector('.nearby-section');
    const nearbyTitle = nearbySection ? nearbySection.querySelector('h2') : null;

    if (featuredSection) featuredSection.style.display = 'block';

    if (nearbyTitle) nearbyTitle.innerHTML = '📍 近くのカフェ';

    displayFeaturedShops();
    displayNearbyShops();
}

async function fetchShops(params, page, size) {
    let queryString = `page=${page}&size=${size}`;
    if (params.keyword) queryString += `&keyword=${encodeURIComponent(params.keyword)}`;
    if (params.minRating) queryString += `&minRating=${params.minRating}`;
    if (params.maxDistance) queryString += `&maxDistance=${params.maxDistance}`;

    const url = `${BACKEND_URL}/api/cafes?${queryString}`;
    
    const response = await fetch(url);
    if (!response.ok) {
        throw new Error(`Failed to fetch cafes. Status: ${response.status}`);
    }
    const data = await response.json();
    return data.content || [];
}


async function displayFeaturedShops() {
    try {
        const featuredShops = await fetchShops({/* no filters */}, 0, 4);
        displayShops(featuredShops, "featuredShops");
    } catch (error) {
        console.error("Featured error:", error);
        document.getElementById("featuredShops").innerHTML = "<p>注目のカフェを読み込めませんでした。</p>";
    }
}

async function displayNearbyShops() {
    try {
        const nearbyShops = await fetchShops({/* no filters */}, 1, 4);
        displayShops(nearbyShops, "nearbyShops");
    } catch (error) {
        console.error("Nearby error:", error);
        document.getElementById("nearbyShops").innerHTML = "<p>近くのカフェを読み込めませんでした。</p>";
    }
}

async function searchCoffeeShops() {
    const keyword = document.getElementById("searchInput").value.trim();
    const minRating = document.getElementById("filterRating").value;
    const maxDistance = document.getElementById("filterDistance").value;

    const params = {
        keyword: keyword,
        minRating: minRating,
        maxDistance: maxDistance
    };
    
    try {
        const results = await fetchShops(params, 0, 20);
        updateSearchResults(results);

    } catch (error) {
        console.error("Search error:", error);
        document.getElementById("nearbyShops").innerHTML = "<p>検索中にエラーが発生しました。</p>";
    }
}

function handleLogout() {
    alert("ログアウトしました。");
}

document.addEventListener("DOMContentLoaded", () => {
    displayFeaturedShops();
    displayNearbyShops();

    const searchInput = document.getElementById("searchInput");
    const filterRating = document.getElementById("filterRating");
    const filterDistance = document.getElementById("filterDistance");

    searchInput?.addEventListener('keypress', function(e) {
        if (e.key === 'Enter') {
            e.preventDefault();
            searchCoffeeShops();
        }
    });
});