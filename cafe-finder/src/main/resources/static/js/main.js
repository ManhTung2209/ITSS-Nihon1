const BACKEND_URL = 'http://localhost:8080';
let currentSearchParams = {};
let currentPage = 0;
const ITEMS_PER_PAGE = 6;
let totalPages = 0;


function getSearchParamsFromURL() {
    const params = new URLSearchParams(window.location.search);
    return {
        keyword: params.get('keyword') || '',
        minRating: params.get('minRating') || '',
        maxDistance: params.get('maxDistance') || '',
        page: parseInt(params.get('page')) || 0
    };
}

function updateURLWithParams(keyword, minRating, maxDistance, page) {
    const params = new URLSearchParams();
    if (keyword) params.set('keyword', keyword);
    if (minRating) params.set('minRating', minRating);
    if (maxDistance) params.set('maxDistance', maxDistance);
    if (page > 0) params.set('page', page);
    
    const newURL = params.toString() ? `${window.location.pathname}?${params.toString()}` : window.location.pathname;
    window.history.replaceState({}, '', newURL);
}

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
        div.style.cursor = "pointer";

        div.onclick = () => {
            window.location.href = `cafes/${shop.id ?? 0}`;
        };

        const ratingStars = '⭐'.repeat(Math.floor(shop.rating || 0));

        const imageFile = shop.image ? shop.image : 'placeholder.png';
        const imgSrc = `${BACKEND_URL}/images/${imageFile}`.replace(/([^:]\/)\/+/g, "$1");

        const description = shop.description ?? "快適な空間";
        
        const statusClass = shop.status === 'opening'
            ? 'status-open'
            : 'status-closed';

        let locationDisplay = "";
        if (shop.distance != null) {
            locationDisplay = `<span> ${shop.distance} km</span>`; 
        } else {
            locationDisplay = `<span>${shop.address || ""}</span>`;
        }

        div.innerHTML = `
            <div class="shop-image-container">
                <img src="${imgSrc}" 
                     alt="${shop.name}" 
                     class="shop-image" 
                     onerror="this.onerror=null; this.src='/images/placeholder.png';">
                <span class="status-badge ${statusClass}"></span>
            </div>
            <div class="shop-info">
                <h3>${shop.name}</h3>
                <p class="shop-rating">
                    ${ratingStars} <span>(${shop.rating ?? "N/A"})</span>
                </p>
                
                <p class="shop-distance">
                    <i class="fas fa-map-marker-alt"></i>
                    ${locationDisplay}
                </p>

                <p class="shop-description">${description}</p>
            </div>
        `;

        container.appendChild(div);
    });
}

function displayPagination(currentPage, totalPages) {
    const paginationContainer = document.getElementById('paginationContainer');
    if (!paginationContainer) return;

    paginationContainer.innerHTML = '';

    if (totalPages <= 1) return;

    const pagination = document.createElement('div');
    pagination.className = 'pagination';

    if (currentPage > 0) {
        const prevBtn = document.createElement('button');
        prevBtn.className = 'pagination-btn';
        prevBtn.innerHTML = '❮';
        prevBtn.onclick = () => goToPage(currentPage - 1);
        pagination.appendChild(prevBtn);
    }

    for (let i = 0; i < totalPages; i++) {
        const pageBtn = document.createElement('button');
        pageBtn.className = 'pagination-btn' + (i === currentPage ? ' active' : '');
        pageBtn.textContent = i + 1;
        pageBtn.onclick = () => goToPage(i);
        pagination.appendChild(pageBtn);
    }

    if (currentPage < totalPages - 1) {
        const nextBtn = document.createElement('button');
        nextBtn.className = 'pagination-btn';
        nextBtn.innerHTML = '❯';
        nextBtn.onclick = () => goToPage(currentPage + 1);
        pagination.appendChild(nextBtn);
    }

    paginationContainer.appendChild(pagination);
}


async function goToPage(page) {
    currentPage = page;
    updateURLWithParams(
        currentSearchParams.keyword, 
        currentSearchParams.minRating, 
        currentSearchParams.maxDistance, 
        page
    );
    try {
        const results = await fetchShops(currentSearchParams, page, ITEMS_PER_PAGE);
        displayShops(results, "nearbyShops");
        displayPagination(currentPage, totalPages);
        window.scrollTo({ top: 0, behavior: 'smooth' });
    } catch (error) {
        console.error("Pagination error:", error);
        document.getElementById("nearbyShops").innerHTML = "<p>検索中にエラーが発生しました。</p>";
    }
}

function updateSearchResults(results, totalResults) {
    const featuredSection = document.querySelector('.featured-section');
    const nearbySection = document.querySelector('.nearby-section');
    const nearbyTitle = nearbySection ? nearbySection.querySelector('h2') : null;

    if (featuredSection) featuredSection.style.display = 'none';

    if (nearbySection) nearbySection.style.display = 'block';

    if (nearbyTitle) nearbyTitle.innerHTML = '🔍 検索結果';

    currentPage = 0;
    totalPages = Math.ceil(totalResults / ITEMS_PER_PAGE);
    displayShops(results, "nearbyShops");
    displayPagination(currentPage, totalPages);
}

async function fetchShops(params, page, size) {
    let queryString = `page=${page}&size=${size}`;

    if (params.keyword) queryString += `&keyword=${encodeURIComponent(params.keyword)}`;
    if (params.minRating) queryString += `&minRating=${params.minRating}`;
    
    if (params.maxDistance) queryString += `&maxDistance=${params.maxDistance}`;

    if (params.sortBy) queryString += `&sortBy=${params.sortBy}`;
    if (params.sortDirection) queryString += `&sortDirection=${params.sortDirection}`;

    const url = `${BACKEND_URL}/api/cafes?${queryString}`;
    
    const response = await fetch(url);
    if (!response.ok) {
        throw new Error(`Failed to fetch cafes. Status: ${response.status}`);
    }
    const data = await response.json();
    return data.content || [];
}

async function fetchShopsWithPagination(params, page, size) {
    let queryString = `page=${page}&size=${size}`;

    if (params.keyword) queryString += `&keyword=${encodeURIComponent(params.keyword)}`;
    if (params.minRating) queryString += `&minRating=${params.minRating}`;
    
    if (params.maxDistance) queryString += `&maxDistance=${params.maxDistance}`;

    if (params.sortBy) queryString += `&sortBy=${params.sortBy}`;
    if (params.sortDirection) queryString += `&sortDirection=${params.sortDirection}`;

    const url = `${BACKEND_URL}/api/cafes?${queryString}`;
    
    const response = await fetch(url);
    if (!response.ok) {
        throw new Error(`Failed to fetch cafes. Status: ${response.status}`);
    }
    const data = await response.json();
    return {
        content: data.content || [],
        totalElements: data.totalElements || 0
    };
}

async function displayFeaturedShops() {
    try {
        const featuredShops = await fetchShops({ sortBy: "rating", sortDirection: "desc" }, 0, 6);
        displayShops(featuredShops, "featuredShops");
    } catch (error) {
        console.error("Featured error:", error);
        document.getElementById("featuredShops").innerHTML = "<p>注目のカフェを読み込めませんでした。</p>";
    }
}

async function displayNearbyShops() {
    try {
        const nearbyShops = await fetchShops({ sortBy: "id", sortDirection: "desc" }, 0, 6);
        displayShops(nearbyShops, "nearbyShops");
    } catch (error) {
        console.error("Nearby error:", error);
        document.getElementById("nearbyShops").innerHTML = "<p>近くのカフェを読み込めませんでした。</p>";
    }
}

async function searchCoffeeShops() {
    const keyword = document.getElementById("searchInput").value.trim();
    const minRating = document.getElementById("filterRating").value;

    const distanceElement = document.getElementById("filterDistance");
    const maxDistance = distanceElement ? distanceElement.value : "";

    const params = {
        keyword: keyword,
        minRating: minRating,
        maxDistance: maxDistance
    };
    
    currentSearchParams = params;
    currentPage = 0;

    updateURLWithParams(keyword, minRating, maxDistance, 0);

    try {
        const { content, totalElements } = await fetchShopsWithPagination(params, 0, ITEMS_PER_PAGE);
        updateSearchResults(content, totalElements);

    } catch (error) {
        console.error("Search error:", error);
        document.getElementById("nearbyShops").innerHTML = "<p>検索中にエラーが発生しました。</p>";
    }
}

document.addEventListener("DOMContentLoaded", async () => {
    const isUserLoggedIn = !!document.getElementById("filterDistance");

    const urlParams = getSearchParamsFromURL();
    const hasSearchParams = urlParams.keyword || urlParams.minRating || urlParams.maxDistance;

    if (hasSearchParams) {
        currentSearchParams = {
            keyword: urlParams.keyword,
            minRating: urlParams.minRating,
            maxDistance: urlParams.maxDistance
        };
        currentPage = urlParams.page;

        const searchInput = document.getElementById("searchInput");
        if(searchInput) searchInput.value = urlParams.keyword;
        
        const filterRating = document.getElementById("filterRating");
        if(filterRating) filterRating.value = urlParams.minRating;

        const filterDistance = document.getElementById("filterDistance");
        if(filterDistance && urlParams.maxDistance) {
            filterDistance.value = urlParams.maxDistance;
        }

        try {
            const { content, totalElements } = await fetchShopsWithPagination(currentSearchParams, currentPage, ITEMS_PER_PAGE);
            updateSearchResults(content, totalElements);
        } catch (error) {
            console.error("Error loading search results:", error);
            displayFeaturedShops();
            if (isUserLoggedIn) displayNearbyShops();
        }
    } 
    else {
        displayFeaturedShops();
        
        if (isUserLoggedIn) {
            displayNearbyShops();
        } else {
            const nearbySection = document.querySelector('.nearby-section');
            if (nearbySection) {
                nearbySection.style.display = 'none';
            }
        }
    }

    const searchInput = document.getElementById("searchInput");

    searchInput?.addEventListener('keypress', function(e) {
        if (e.key === 'Enter') {
            e.preventDefault();
            searchCoffeeShops();
        }
    });

});