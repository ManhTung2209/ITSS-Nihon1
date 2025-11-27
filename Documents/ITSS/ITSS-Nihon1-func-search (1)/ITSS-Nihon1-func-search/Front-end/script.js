// サンプルデータ: コーヒーショップ
const coffeeShops = [
    {
        id: 1,
        name: "カフェ・ド・パリ 渋谷店",
        rating: 4.8,
        address: "東京都渋谷区渋谷1-2-3",
        distance: 0.5,
        lat: 35.6595,
        lng: 139.7004,
        image: "https://images.unsplash.com/photo-1554118811-1e0d58224f24?w=400",
        description: "フランス風の洗練された雰囲気のカフェ。自家焙煎のコーヒーと手作りスイーツが人気です。",
        featured: true,
        menu: [
            { name: "エスプレッソ", price: "¥450" },
            { name: "カプチーノ", price: "¥520" },
            { name: "カフェラテ", price: "¥480" },
            { name: "クロワッサン", price: "¥380" },
            { name: "チョコレートケーキ", price: "¥650" },
            { name: "ティラミス", price: "¥680" }
        ],
        reviews: [
            { author: "田中太郎", rating: 5, text: "コーヒーの香りが素晴らしい！雰囲気も最高です。" },
            { author: "佐藤花子", rating: 4.5, text: "クロワッサンがとても美味しかったです。また来たい。" }
        ]
    },
    {
        id: 2,
        name: "スターバックスコーヒー 新宿南口店",
        rating: 4.5,
        address: "東京都新宿区新宿3-38-1",
        distance: 1.2,
        lat: 35.6897,
        lng: 139.7008,
        image: "https://images.unsplash.com/photo-1521017432531-fbd92d768814?w=400",
        description: "世界的に有名なコーヒーチェーン。WiFiと電源完備で作業に最適。",
        featured: true,
        menu: [
            { name: "ドリップコーヒー", price: "¥380" },
            { name: "キャラメルマキアート", price: "¥520" },
            { name: "抹茶ラテ", price: "¥550" },
            { name: "ニューヨークチーズケーキ", price: "¥480" },
            { name: "シナモンロール", price: "¥420" }
        ],
        reviews: [
            { author: "鈴木一郎", rating: 4.5, text: "安定の味。WiFiが速くて仕事がはかどります。" }
        ]
    },
    {
        id: 3,
        name: "ブルーボトルコーヒー 青山カフェ",
        rating: 4.9,
        address: "東京都港区南青山3-13-14",
        distance: 2.3,
        lat: 35.6662,
        lng: 139.7126,
        image: "https://images.unsplash.com/photo-1501339847302-ac426a4a7cbb?w=400",
        description: "シングルオリジンのスペシャルティコーヒーが楽しめる。ミニマルでおしゃれな空間。",
        featured: false,
        menu: [
            { name: "ハンドドリップコーヒー", price: "¥580" },
            { name: "カフェラテ", price: "¥620" },
            { name: "アイスコーヒー", price: "¥550" },
            { name: "ワッフル", price: "¥720" },
            { name: "アーモンドクロワッサン", price: "¥450" }
        ],
        reviews: [
            { author: "山田美咲", rating: 5, text: "コーヒーの品質が素晴らしい。少し高いけど価値あり。" },
            { author: "伊藤健", rating: 4.8, text: "静かで落ち着いた雰囲気。デートにぴったり。" }
        ]
    },
    {
        id: 4,
        name: "コメダ珈琲店 池袋東口店",
        rating: 4.3,
        address: "東京都豊島区南池袋1-25-1",
        distance: 3.5,
        lat: 35.7295,
        lng: 139.7109,
        image: "https://images.unsplash.com/photo-1495474472287-4d71bcdd2085?w=400",
        description: "名古屋発祥の喫茶店。ボリューム満点のモーニングセットが人気。",
        featured: false,
        menu: [
            { name: "ブレンドコーヒー", price: "¥460" },
            { name: "ウインナーコーヒー", price: "¥540" },
            { name: "シロノワール", price: "¥780" },
            { name: "カツサンド", price: "¥880" },
            { name: "小倉トースト", price: "¥520" }
        ],
        reviews: [
            { author: "高橋翔太", rating: 4.5, text: "シロノワールが絶品！ボリュームたっぷりです。" }
        ]
    },
    {
        id: 5,
        name: "猿田彦珈琲 恵比寿本店",
        rating: 4.7,
        address: "東京都渋谷区恵比寿1-6-6",
        distance: 1.8,
        lat: 35.6467,
        lng: 139.7108,
        image: "https://images.unsplash.com/photo-1511920170033-f8396924c348?w=400",
        description: "日本のスペシャルティコーヒーのパイオニア。バリスタの技術が光る。",
        featured: true,
        menu: [
            { name: "オリジナルブレンド", price: "¥500" },
            { name: "カフェラテ", price: "¥580" },
            { name: "フラットホワイト", price: "¥620" },
            { name: "パウンドケーキ", price: "¥480" },
            { name: "スコーン", price: "¥420" }
        ],
        reviews: [
            { author: "渡辺愛", rating: 4.8, text: "コーヒーへのこだわりが感じられる。香りが最高。" },
            { author: "小林大輔", rating: 4.6, text: "バリスタさんが親切で知識豊富です。" }
        ]
    },
    {
        id: 6,
        name: "タリーズコーヒー 六本木ヒルズ店",
        rating: 4.4,
        address: "東京都港区六本木6-10-1",
        distance: 2.7,
        lat: 35.6604,
        lng: 139.7292,
        image: "https://images.unsplash.com/photo-1509042239860-f550ce710b93?w=400",
        description: "くつろげる空間とこだわりのコーヒー。スイーツメニューも充実。",
        featured: false,
        menu: [
            { name: "本日のコーヒー", price: "¥390" },
            { name: "ロイヤルミルクティー", price: "¥480" },
            { name: "抹茶ラテ", price: "¥520" },
            { name: "ベルギーワッフル", price: "¥580" },
            { name: "カップケーキ", price: "¥420" }
        ],
        reviews: [
            { author: "中村陽子", rating: 4.5, text: "落ち着いた雰囲気で読書に最適です。" }
        ]
    }
];

// ホームページ: 注目のカフェを表示
function displayFeaturedShops() {
    const container = document.getElementById('featuredShops');
    if (!container) return;

    const featured = coffeeShops.filter(shop => shop.featured);
    container.innerHTML = featured.map(shop => createShopCard(shop)).join('');
}

// ホームページ: 近くのカフェを表示
function displayNearbyShops() {
    const container = document.getElementById('nearbyShops');
    if (!container) return;

    const nearby = coffeeShops.sort((a, b) => a.distance - b.distance);
    container.innerHTML = nearby.map(shop => createShopCard(shop)).join('');
}

// カフェカードのHTMLを生成
function createShopCard(shop) {
    const stars = '⭐'.repeat(Math.floor(shop.rating));
    return `
        <div class="shop-card" onclick="goToDetail(${shop.id})">
            <img src="${shop.image}" alt="${shop.name}" class="shop-image">
            <div class="shop-info">
                <h3 class="shop-name">${shop.name}</h3>
                <div class="shop-rating">${stars} ${shop.rating}</div>
                <p class="shop-address">📍 ${shop.address}</p>
                <p class="shop-distance">🚶 ${shop.distance}km</p>
            </div>
        </div>
    `;
}

// 検索機能
function searchCoffeeShops() {
    const searchTerm = document.getElementById('searchInput').value.toLowerCase();
    const ratingFilter = document.getElementById('filterRating').value;
    const distanceFilter = document.getElementById('filterDistance').value;

    let filtered = coffeeShops.filter(shop => {
        const matchesSearch = shop.name.toLowerCase().includes(searchTerm) || 
                            shop.address.toLowerCase().includes(searchTerm);
        const matchesRating = !ratingFilter || shop.rating >= parseFloat(ratingFilter);
        const matchesDistance = !distanceFilter || shop.distance <= parseFloat(distanceFilter);
        
        return matchesSearch && matchesRating && matchesDistance;
    });

    const nearbyContainer = document.getElementById('nearbyShops');
    if (nearbyContainer) {
        if (filtered.length === 0) {
            nearbyContainer.innerHTML = '<p style="text-align:center; color: var(--text-light);">検索結果が見つかりませんでした。</p>';
        } else {
            nearbyContainer.innerHTML = filtered.map(shop => createShopCard(shop)).join('');
            // Hiển thị thông báo tìm kiếm/lọc thành công
            showSearchNotification(filtered.length, searchTerm, ratingFilter, distanceFilter);
        }
    }
}

function showSearchNotification(count, searchTerm, ratingFilter, distanceFilter) {
    const oldNotification = document.querySelector('.search-notification');
    if (oldNotification) {
        oldNotification.remove();
    }

    // 操作タイプを判定
    const hasSearch = searchTerm && searchTerm.trim() !== '';
    const hasFilter = ratingFilter || distanceFilter;
    
    let message = '';
    if (hasSearch && hasFilter) {
        message = `検索とフィルター完了！${count}件のカフェが見つかりました。`;
    } else if (hasSearch) {
        message = `検索完了！${count}件のカフェが見つかりました。`;
    } else if (hasFilter) {
        message = `フィルター完了！${count}件のカフェが見つかりました。`;
    } else {
        message = `${count}件のカフェを表示中。`;
    }

    const notification = document.createElement('div');
    notification.className = 'search-notification';
    notification.innerHTML = `
        <i class="fas fa-check-circle"></i>
        <span>${message}</span>
    `;
    
    document.body.appendChild(notification);

    setTimeout(() => {
        notification.classList.add('hide');
        setTimeout(() => {
            notification.remove();
        }, 300);
    }, 3000);
}

// 詳細ページへ遷移
function goToDetail(shopId) {
    window.location.href = `detail.html?id=${shopId}`;
}

// 詳細ページ: カフェ詳細を表示
function displayShopDetail() {
    const container = document.getElementById('shopDetail');
    if (!container) return;

    const urlParams = new URLSearchParams(window.location.search);
    const shopId = parseInt(urlParams.get('id'));
    const shop = coffeeShops.find(s => s.id === shopId);

    if (!shop) {
        container.innerHTML = '<p>カフェが見つかりませんでした。</p>';
        return;
    }

    const stars = '⭐'.repeat(Math.floor(shop.rating));
    
    container.innerHTML = `
        <div class="detail-header">
            <img src="${shop.image}" alt="${shop.name}" class="detail-image">
        </div>
        <div class="detail-content">
            <div class="detail-title-row">
                <h1 class="detail-title">${shop.name}</h1>
                <a href="map.html?id=${shop.id}" class="map-button-inline">🗺️ 地図で見る</a>
            </div>
            <div class="detail-rating">${stars} ${shop.rating}</div>
            
            <div class="detail-info">
                <h3>📍 住所</h3>
                <p>${shop.address}</p>
                
                <h3>🚶 距離</h3>
                <p>${shop.distance}km</p>
                
                <h3>ℹ️ 説明</h3>
                <p>${shop.description}</p>
            </div>

            <div class="menu-section">
                <h3>☕ メニュー</h3>
                <div class="menu-grid">
                    ${shop.menu.map(item => `
                        <div class="menu-item">
                            <div class="menu-item-name">${item.name}</div>
                            <div class="menu-item-price">${item.price}</div>
                        </div>
                    `).join('')}
                </div>
            </div>

            <div class="reviews-section">
                <h3>💬 レビュー</h3>
                ${shop.reviews.map(review => {
                    const reviewStars = '⭐'.repeat(Math.floor(review.rating));
                    return `
                        <div class="review-card">
                            <button class="review-report-btn" onclick="reportReview('${review.author}'); return false;" title="レビューを報告">
                                !
                            </button>
                            <div class="review-author">${review.author}</div>
                            <div class="review-rating">${reviewStars} ${review.rating}</div>
                            <p class="review-text">${review.text}</p>
                        </div>
                    `;
                }).join('')}
                
                <!-- レビュー投稿フォーム -->
                <div class="review-form-container">
                    <h3>✍️ レビューを投稿する</h3>
                    <form class="review-form" id="reviewForm" onsubmit="submitReview(event, ${shop.id})">
                        <div class="form-group">
                            <label for="reviewAuthor">お名前 <span class="required">*</span></label>
                            <input type="text" id="reviewAuthor" name="reviewAuthor" required placeholder="山田太郎">
                        </div>
                        
                        <div class="form-group">
                            <label for="reviewRating">評価 <span class="required">*</span></label>
                            <div class="star-rating" id="starRating">
                                <span class="star" data-rating="1">☆</span>
                                <span class="star" data-rating="2">☆</span>
                                <span class="star" data-rating="3">☆</span>
                                <span class="star" data-rating="4">☆</span>
                                <span class="star" data-rating="5">☆</span>
                            </div>
                            <input type="hidden" id="reviewRating" name="reviewRating" required>
                            <small class="rating-text">クリックして評価してください</small>
                        </div>
                        
                        <div class="form-group">
                            <label for="reviewText">レビュー内容 <span class="required">*</span></label>
                            <textarea id="reviewText" name="reviewText" required rows="4" placeholder="こちらのカフェについてのご感想をお聞かせください..."></textarea>
                            <small class="char-count"><span id="charCount">0</span>/500文字</small>
                        </div>
                        
                        <button type="submit" class="submit-review-btn">
                            <i class="fas fa-paper-plane"></i> レビューを投稿
                        </button>
                    </form>
                </div>
            </div>
        </div>
    `;
    
    // 星評価の初期化
    initializeStarRating();
    
    // 文字数カウンターの初期化
    const reviewTextArea = document.getElementById('reviewText');
    if (reviewTextArea) {
        reviewTextArea.addEventListener('input', function() {
            const charCount = document.getElementById('charCount');
            const count = this.value.length;
            charCount.textContent = count;
            
            if (count > 500) {
                this.value = this.value.substring(0, 500);
                charCount.textContent = '500';
            }
        });
    }
}

// 地図ページ: 地図を初期化
function initializeMap() {
    const mapElement = document.getElementById('map');
    if (!mapElement) return;

    // 東京の中心座標
    const map = L.map('map').setView([35.6812, 139.7671], 13);

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '© OpenStreetMap contributors'
    }).addTo(map);

    // カフェのマーカーを追加
    coffeeShops.forEach(shop => {
        const marker = L.marker([shop.lat, shop.lng]).addTo(map);
        marker.bindPopup(`
            <strong>${shop.name}</strong><br>
            ${shop.address}<br>
            ⭐ ${shop.rating}<br>
            <a href="detail.html?id=${shop.id}">詳細を見る</a>
        `);
    });

    // サイドバーにカフェリストを表示
    const shopList = document.getElementById('shopList');
    if (shopList) {
        shopList.innerHTML = coffeeShops.map(shop => `
            <div class="shop-list-item" onclick="focusShop(${shop.lat}, ${shop.lng})">
                <strong>${shop.name}</strong><br>
                <small>⭐ ${shop.rating} • ${shop.distance}km</small>
            </div>
        `).join('');
    }

    // URLパラメータで特定のカフェにフォーカス
    const urlParams = new URLSearchParams(window.location.search);
    const shopId = parseInt(urlParams.get('id'));
    if (shopId) {
        const shop = coffeeShops.find(s => s.id === shopId);
        if (shop) {
            map.setView([shop.lat, shop.lng], 15);
        }
    }
}

function focusShop(lat, lng) {
    // この関数は地図ページで使用されます
    const mapElement = document.getElementById('map');
    if (mapElement && window.map) {
        window.map.setView([lat, lng], 16);
    }
}

// ログイン処理
function handleLogin(event) {
    event.preventDefault();
    
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    const rememberMe = document.getElementById('rememberMe').checked;
    
    // バリデーション
    clearErrors();
    
    if (!validateEmail(email)) {
        showError('emailError', '有効なメールアドレスを入力してください');
        return;
    }
    
    if (password.length < 6) {
        showError('passwordError', 'パスワードは6文字以上で入力してください');
        return;
    }
    
    // ローカルストレージからユーザー情報を取得
    const users = JSON.parse(localStorage.getItem('users') || '[]');
    const user = users.find(u => u.email === email && u.password === password);
    
    if (user) {
        // ログイン成功
        localStorage.setItem('currentUser', JSON.stringify(user));
        if (rememberMe) {
            localStorage.setItem('rememberMe', 'true');
        }
        alert('ログインに成功しました！');
        window.location.href = 'index.html';
    } else {
        showError('loginError', 'メールアドレスまたはパスワードが正しくありません');
    }
}

// 新規登録処理
function handleRegister(event) {
    event.preventDefault();
    
    const name = document.getElementById('name').value;
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    const confirmPassword = document.getElementById('confirmPassword').value;
    const agreeTerms = document.getElementById('agreeTerms').checked;
    
    // バリデーション
    clearErrors();
    
    if (name.length < 2) {
        showError('nameError', '名前は2文字以上で入力してください');
        return;
    }
    
    if (!validateEmail(email)) {
        showError('emailError', '有効なメールアドレスを入力してください');
        return;
    }
    
    if (password.length < 6) {
        showError('passwordError', 'パスワードは6文字以上で入力してください');
        return;
    }
    
    if (password !== confirmPassword) {
        showError('confirmPasswordError', 'パスワードが一致しません');
        return;
    }
    
    if (!agreeTerms) {
        showError('registerError', '利用規約に同意してください');
        return;
    }
    
    // ユーザー登録
    const users = JSON.parse(localStorage.getItem('users') || '[]');
    
    if (users.find(u => u.email === email)) {
        showError('emailError', 'このメールアドレスは既に登録されています');
        return;
    }
    
    const newUser = { name, email, password, phone: '' };
    users.push(newUser);
    localStorage.setItem('users', JSON.stringify(users));
    
    alert('登録が完了しました！ログインしてください。');
    window.location.href = 'login.html';
}

// プロフィール更新
function handleProfileUpdate(event) {
    event.preventDefault();
    
    const name = document.getElementById('profileName').value;
    const email = document.getElementById('profileEmail').value;
    const phone = document.getElementById('profilePhone').value;
    
    const currentUser = JSON.parse(localStorage.getItem('currentUser') || '{}');
    const users = JSON.parse(localStorage.getItem('users') || '[]');
    
    const userIndex = users.findIndex(u => u.email === currentUser.email);
    if (userIndex !== -1) {
        users[userIndex] = { ...users[userIndex], name, email, phone };
        localStorage.setItem('users', JSON.stringify(users));
        localStorage.setItem('currentUser', JSON.stringify(users[userIndex]));
        
        showSuccess('profileSuccess', 'プロフィールを更新しました');
    }
}

// パスワード変更
function handlePasswordChange(event) {
    event.preventDefault();
    
    const currentPassword = document.getElementById('currentPassword').value;
    const newPassword = document.getElementById('newPassword').value;
    const confirmNewPassword = document.getElementById('confirmNewPassword').value;
    
    clearErrors();
    
    if (newPassword.length < 6) {
        showError('passwordError', 'パスワードは6文字以上で入力してください');
        return;
    }
    
    if (newPassword !== confirmNewPassword) {
        showError('passwordError', 'パスワードが一致しません');
        return;
    }
    
    const currentUser = JSON.parse(localStorage.getItem('currentUser') || '{}');
    
    if (currentUser.password !== currentPassword) {
        showError('passwordError', '現在のパスワードが正しくありません');
        return;
    }
    
    const users = JSON.parse(localStorage.getItem('users') || '[]');
    const userIndex = users.findIndex(u => u.email === currentUser.email);
    
    if (userIndex !== -1) {
        users[userIndex].password = newPassword;
        localStorage.setItem('users', JSON.stringify(users));
        currentUser.password = newPassword;
        localStorage.setItem('currentUser', JSON.stringify(currentUser));
        
        showSuccess('passwordSuccess', 'パスワードを変更しました');
        event.target.reset();
    }
}

// ログアウト
function handleLogout() {
    if (confirm('ログアウトしますか？')) {
        localStorage.removeItem('currentUser');
        localStorage.removeItem('rememberMe');
        
        // プロトタイプモード: ログアウト成功のメッセージを表示してホームに戻る
        showReviewSuccessNotification('ログアウトしました。');
        
        setTimeout(() => {
            window.location.href = 'index.html';
        }, 1500);
    }
}

// パスワードを忘れた場合の処理
function handleForgotPassword() {
    const email = prompt('登録されているメールアドレスを入力してください：');
    
    if (!email) {
        return; // キャンセルされた場合
    }
    
    if (!validateEmail(email)) {
        alert('有効なメールアドレスを入力してください');
        return;
    }
    
    // ユーザーの存在確認
    const users = JSON.parse(localStorage.getItem('users') || '[]');
    const user = users.find(u => u.email === email);
    
    if (user) {
        // プロトタイプモード: 実際にはメールを送信せず、メッセージのみ表示
        alert(`パスワードリセットのリンクを ${email} に送信しました。\n\n（プロトタイプモード：実際のメールは送信されません）`);
        console.log('Password reset requested for:', email);
    } else {
        alert('このメールアドレスは登録されていません');
    }
}

// パスワードリセットフォームの送信処理
function handleForgotPasswordSubmit(event) {
    event.preventDefault();
    
    const email = document.getElementById('email').value;
    const emailError = document.getElementById('emailError');
    const successMessage = document.getElementById('successMessage');
    
    // エラーをクリア
    emailError.textContent = '';
    successMessage.style.display = 'none';
    
    // バリデーション
    if (!validateEmail(email)) {
        emailError.textContent = '有効なメールアドレスを入力してください';
        return;
    }
    
    // プロトタイプモード: UI表示のみ
    successMessage.innerHTML = `
        <i class="fas fa-check-circle"></i> 
        パスワードリセットのリンクを <strong>${email}</strong> に送信しました。<br>
        メールをご確認ください。
    `;
    successMessage.style.display = 'block';
    
    // フォームをリセット
    document.getElementById('forgotPasswordForm').reset();
    
    console.log('Password reset link sent to:', email);
    
    // 3秒後にログインページに戻る
    setTimeout(() => {
        window.location.href = 'login.html';
    }, 3000);
}

// アカウント削除
function handleDeleteAccount() {
    if (confirm('本当にアカウントを削除しますか？この操作は取り消せません。')) {
        const currentUser = JSON.parse(localStorage.getItem('currentUser') || '{}');
        const users = JSON.parse(localStorage.getItem('users') || '[]');
        
        const filteredUsers = users.filter(u => u.email !== currentUser.email);
        localStorage.setItem('users', JSON.stringify(filteredUsers));
        localStorage.removeItem('currentUser');
        
        alert('アカウントを削除しました');
        window.location.href = 'register.html';
    }
}

// ユーザープロフィール読み込み
function loadUserProfile() {
    const currentUser = JSON.parse(localStorage.getItem('currentUser') || '{}');
    
    if (currentUser.email) {
        const nameInput = document.getElementById('profileName');
        const emailInput = document.getElementById('profileEmail');
        const phoneInput = document.getElementById('profilePhone');
        
        if (nameInput) nameInput.value = currentUser.name || '';
        if (emailInput) emailInput.value = currentUser.email || '';
        if (phoneInput) phoneInput.value = currentUser.phone || '';
    }
}

// ヘルパー関数
function validateEmail(email) {
    const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return re.test(email);
}

function showError(elementId, message) {
    const element = document.getElementById(elementId);
    if (element) {
        element.textContent = message;
        element.style.display = 'block';
    }
}

function showSuccess(elementId, message) {
    const element = document.getElementById(elementId);
    if (element) {
        element.textContent = message;
        element.style.display = 'block';
        setTimeout(() => {
            element.style.display = 'none';
        }, 3000);
    }
}

function clearErrors() {
    const errors = document.querySelectorAll('.error-message');
    errors.forEach(error => {
        error.textContent = '';
        error.style.display = 'none';
    });
}

// レビュー投稿機能
function initializeStarRating() {
    const stars = document.querySelectorAll('.star');
    const ratingInput = document.getElementById('reviewRating');
    let selectedRating = 0;
    
    stars.forEach(star => {
        // ホバー効果
        star.addEventListener('mouseenter', function() {
            const rating = parseInt(this.getAttribute('data-rating'));
            highlightStars(rating);
        });
        
        // クリック
        star.addEventListener('click', function() {
            selectedRating = parseInt(this.getAttribute('data-rating'));
            ratingInput.value = selectedRating;
            highlightStars(selectedRating);
            
            // 評価テキストを更新
            const ratingText = document.querySelector('.rating-text');
            const ratingLabels = ['', 'とても悪い', '悪い', '普通', '良い', 'とても良い'];
            ratingText.textContent = `${selectedRating}つ星 - ${ratingLabels[selectedRating]}`;
            ratingText.style.color = 'var(--primary-color)';
        });
    });
    
    // マウスアウト時に選択した評価に戻す
    const starRating = document.getElementById('starRating');
    if (starRating) {
        starRating.addEventListener('mouseleave', function() {
            highlightStars(selectedRating);
        });
    }
    
    function highlightStars(rating) {
        stars.forEach(star => {
            const starRating = parseInt(star.getAttribute('data-rating'));
            if (starRating <= rating) {
                star.textContent = '★';
                star.style.color = '#FFD700';
            } else {
                star.textContent = '☆';
                star.style.color = '#DDD';
            }
        });
    }
}

function submitReview(event, shopId) {
    event.preventDefault();
    
    const author = document.getElementById('reviewAuthor').value;
    const rating = parseFloat(document.getElementById('reviewRating').value);
    const text = document.getElementById('reviewText').value;
    
    if (!rating) {
        alert('評価を選択してください');
        return;
    }
    
    // 新しいレビューを作成
    const newReview = {
        author: author,
        rating: rating,
        text: text,
        date: new Date().toISOString()
    };
    
    // カフェのレビューに追加
    const shop = coffeeShops.find(s => s.id === shopId);
    if (shop) {
        shop.reviews.unshift(newReview);
        
        // 成功メッセージを表示
        showReviewSuccessNotification();
        
        // フォームをリセット
        document.getElementById('reviewForm').reset();
        document.getElementById('charCount').textContent = '0';
        
        // 星をリセット
        const stars = document.querySelectorAll('.star');
        stars.forEach(star => {
            star.textContent = '☆';
            star.style.color = '#DDD';
        });
        
        // 評価テキストをリセット
        const ratingText = document.querySelector('.rating-text');
        ratingText.textContent = 'クリックして評価してください';
        ratingText.style.color = '';
        
        // ページを再読み込みしてレビューを表示
        setTimeout(() => {
            displayShopDetail();
        }, 1500);
    }
}

function showReviewSuccessNotification(message = 'レビューを投稿しました！ありがとうございます。') {
    const oldNotification = document.querySelector('.search-notification');
    if (oldNotification) {
        oldNotification.remove();
    }

    const notification = document.createElement('div');
    notification.className = 'search-notification';
    notification.innerHTML = `
        <i class="fas fa-check-circle"></i>
        <span>${message}</span>
    `;
    
    document.body.appendChild(notification);

    setTimeout(() => {
        notification.classList.add('hide');
        setTimeout(() => {
            notification.remove();
        }, 300);
    }, 3000);
}

function reportReview(author) {
    const confirmed = confirm(''+author+'???????????????\n\n???????????????????????');
    
    if (confirmed) {
        showReviewSuccessNotification('??????????????????????');
        console.log('Reported review by:', author);
    }
}
