// 🔍 Tìm kiếm cafe
async function searchCoffeeShops() {
    const keyword = document.getElementById("searchInput").value.toLowerCase();
    const ratingFilter = document.getElementById("filterRating").value;
    const distanceFilter = document.getElementById("filterDistance").value;

    try {
        const response = await fetch(`/api/cafes/search?name=${encodeURIComponent(keyword)}&page=0&size=10`);
        if (!response.ok) throw new Error("Search request failed");
        const data = await response.json();
        let results = data.content;

        // Filter theo rating nếu có
        if (ratingFilter) {
            results = results.filter(c => c.rating && Number(c.rating) >= Number(ratingFilter));
        }

        // Nếu backend có khoảng cách thì filter distance
        // results = results.filter(c => c.distance <= Number(distanceFilter));

        displayShops(results, "featuredShops"); // Hiển thị ở section Featured
    } catch (error) {
        console.error("Search error:", error);
    }
}

// 🌟 Hiển thị cafe nổi bật
async function displayFeaturedShops() {
    try {
        const response = await fetch(`/api/cafes/all?page=0&size=4`);
        if (!response.ok) throw new Error("Failed to fetch featured cafes");
        const data = await response.json();
        displayShops(data.content, "featuredShops");
    } catch (error) {
        console.error("Featured error:", error);
    }
}

// 📍 Hiển thị cafe gần đây
async function displayNearbyShops() {
    try {
        const response = await fetch(`/api/cafes/all?page=1&size=4`);
        if (!response.ok) throw new Error("Failed to fetch nearby cafes");
        const data = await response.json();
        displayShops(data.content, "nearbyShops");
    } catch (error) {
        console.error("Nearby error:", error);
    }
}

// 📦 Hàm render shop
function displayShops(list, elementId) {
    const container = document.getElementById(elementId);
    container.innerHTML = "";

    if (!list || list.length === 0) {
        container.innerHTML = "<p>該当するカフェがありません。</p>";
        return;
    }

    list.forEach(shop => {
        const div = document.createElement("div");
        div.className = "shop-card";

        // Nếu không có image thì hiển thị placeholder
        const imgSrc = shop.image ? shop.image : "/images/placeholder.png";

        div.innerHTML = `
            <img src="${imgSrc}" alt="${shop.name}" class="shop-image">
            <h3>${shop.name}</h3>
            <p>⭐ 評価: ${shop.rating ?? "N/A"}</p>
            <p>📍 住所: ${shop.address ?? "不明"}</p>
        `;

        container.appendChild(div);
    });
}

// ⚡ Khi load trang
document.addEventListener("DOMContentLoaded", () => {
    displayFeaturedShops();
    displayNearbyShops();
});
