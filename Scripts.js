 
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
   
 // ===================== GLOBAL VARIABLES =====================
        let currentUser = null;
        let currentCurrency = 'USD';
        let currentLanguage = 'en';
        let conversionRate = 3.75; // USD to SAR
        let cart = [];
        let wishlist = [];
        
        // Translation dictionaries
        const translations = {
            en: {
                // Navigation
                'nav-home': 'Home',
                'nav-games': 'Games',
                'nav-library': 'My Library',
                'nav-admin': 'Admin Panel',
                'nav-language': 'Language',
                'nav-currency': 'Currency',
                'nav-cart': 'Cart',
                'nav-account': 'Account',
                'menu-login': 'Login',
                'menu-register': 'Register',
                'menu-profile': 'Profile',
                'menu-wishlist': 'Wishlist',
                'menu-logout': 'Logout',
                
                // Home Page
                'hero-title': 'Discover Your Next Favorite Game',
                'hero-subtitle': 'Explore thousands of games across all genres. From action-packed adventures to mind-bending puzzles.',
                'hero-browse-btn': 'Browse Games',
                'hero-deals-btn': 'View Deals',
                'featured-badge-text': 'FEATURED',
                'featured-games-title': 'Featured Games',
                'filter-all': 'All',
                'filter-fps': 'FPS',
                'filter-rpg': 'RPG',
                'categories-title': 'Browse Categories',
                'cat-fps': 'FPS',
                'cat-rpg': 'RPG',
                'cat-action': 'Action-Adventure',
                'cat-mmo': 'MMO',
                'cat-arcade': 'Arcade',
                'cat-racing': 'Racing',
                'cat-sports': 'Sports',
                'cat-strategy': 'Strategy',
                
                // Games Page
                'all-games-title': 'Browse All Games',
                'filter-all-games': 'All Games',
                'filter-games-fps': 'FPS',
                'filter-games-rpg': 'RPG',
                'filter-games-action': 'Action-Adventure',
                'filter-games-mmo': 'MMO',
                'filter-games-arcade': 'Arcade',
                'filter-games-racing': 'Racing',
                'filter-games-sports': 'Sports',
                'filter-games-strategy': 'Strategy',
                'filter-games-horror': 'Horror',
                'search-btn': 'Search',
                
                // Library Page
                'library-title': 'My Game Library',
                'library-login-title': 'Access Your Purchased Games & Digital Codes',
                'library-login-subtitle': 'Enter your account email to view your library',
                'library-view-btn': 'View Library',
                'library-back-btn': 'Back',
                'library-games-title': 'Purchased Games',
                'library-codes-title': 'Digital Codes & Subscriptions',
                'library-history-title': 'Purchase History',
                'history-date': 'Date',
                'history-item': 'Item',
                'history-type': 'Type',
                'history-price': 'Price',
                'history-status': 'Status',
                
                // Admin Panel
                'admin-title': 'Admin Panel',
                'admin-analytics-btn': 'Sales Analytics',
                'admin-games-btn': 'Game Management',
                'admin-users-btn': 'User Management',
                'admin-settings-btn': 'Settings',
                'sales-chart-title': 'Sales Overview (Last 30 Days)',
                'stats-title': 'Quick Stats',
                'total-revenue': 'Total Revenue',
                'total-orders': 'Total Orders',
                'total-users': 'Total Users',
                'total-games': 'Total Games',
                'top-games-title': 'Top Selling Games',
                'top-game': 'Game',
                'top-sales': 'Sales',
                'top-revenue': 'Revenue',
                'recent-orders-title': 'Recent Orders',
                'order-id': 'Order ID',
                'order-user': 'User',
                'order-amount': 'Amount',
                'order-date': 'Date',
                'add-game-title': 'Add New Game',
                'game-title-label': 'Game Title',
                'game-category-label': 'Category',
                'game-price-label': 'Price (USD)',
                'game-image-label': 'Image URL',
                'game-description-label': 'Description',
                'game-developer-label': 'Developer',
                'add-game-btn': 'Add Game',
                'manage-games-title': 'Manage Existing Games',
                'game-id': 'ID',
                'game-name': 'Game Name',
                'game-cat': 'Category',
                'game-price-col': 'Price',
                'game-actions': 'Actions',
                'user-management-title': 'User Management',
                'user-id': 'ID',
                'user-email': 'Email',
                'user-name': 'Name',
                'user-join-date': 'Join Date',
                'user-orders': 'Orders',
                'user-total': 'Total Spent',
                'user-status': 'Status',
                'user-action': 'Actions',
                'site-settings-title': 'Site Settings',
                'site-name-label': 'Site Name',
                'default-currency-label': 'Default Currency',
                'default-language-label': 'Default Language',
                'tax-rate-label': 'Tax Rate (%)',
                'maintenance-label': 'Maintenance Mode',
                'save-settings-btn': 'Save Settings',
                'payment-settings-title': 'Payment Settings',
                'payment-methods-label': 'Enabled Payment Methods',
                'paypal-label': 'PayPal',
                'credit-card-label': 'Credit/Debit Card',
                'crypto-label': 'Cryptocurrency',
                'min-purchase-label': 'Minimum Purchase Amount',
                'currency-rate-label': 'USD to SAR Rate',
                'save-payment-btn': 'Save Payment Settings',
                
                // Login/Register
                'login-title': 'Login',
                'login-email-label': 'Email',
                'login-password-label': 'Password',
                'login-submit-btn': 'Login',
                'login-register-link': 'Don\'t have an account? Register',
                'register-title': 'Register',
                'register-name-label': 'Full Name',
                'register-email-label': 'Email',
                'register-password-label': 'Password',
                'register-confirm-label': 'Confirm Password',
                'register-submit-btn': 'Create Account',
                'register-login-link': 'Already have an account? Login',
                
                // Profile
                'profile-title': 'My Profile',
                'profile-edit-btn': 'Edit Profile',
                'profile-info-title': 'Account Information',
                'profile-fullname-label': 'Full Name:',
                'profile-email-label': 'Email:',
                'profile-joindate-label': 'Join Date:',
                'profile-totalorders-label': 'Total Orders:',
                'profile-order-history': 'Order History',
                'profile-order-id': 'Order ID',
                'profile-order-date': 'Date',
                'profile-order-items': 'Items',
                'profile-order-total': 'Total',
                'profile-order-status': 'Status',
                
                // Wishlist
                'wishlist-title': 'My Wishlist',
                'wishlist-empty-title': 'Your wishlist is empty',
                'wishlist-empty-text': 'Add games to your wishlist by clicking the heart icon on any game',
                'wishlist-browse-btn': 'Browse Games',
                
                // Cart
                'cart-title': 'Shopping Cart',
                'cart-empty-title': 'Your cart is empty',
                'cart-empty-text': 'Add games to your cart to get started',
                'cart-browse-btn': 'Browse Games',
                'cart-summary-title': 'Order Summary',
                'cart-subtotal-label': 'Subtotal:',
                'cart-tax-label': 'Tax (15%):',
                'cart-total-label': 'Total:',
                'cart-checkout-btn': 'Proceed to Checkout',
                'cart-continue-btn': 'Continue Shopping',
                
                // Checkout
                'checkout-title': 'Checkout',
                'payment-details-title': 'Payment Details',
                'card-number-label': 'Card Number',
                'card-number-hint': '12-16 digits required',
                'expiry-date-label': 'Expiry Date',
                'cvv-label': 'CVV',
                'card-name-label': 'Cardholder Name',
                'payment-submit-btn': 'Complete Payment',
                'card-preview-title': 'Virtual Payment Card',
                'order-summary-title': 'Order Summary',
                'order-total-label': 'Total:',
                
                // Payment Success
                'payment-success-title': 'Payment Successful!',
                'payment-success-text': 'Thank you for your purchase. Your order has been confirmed.',
                'receipt-title': 'GAMEHUB - DIGITAL RECEIPT',
                'receipt-order-id': 'Order ID:',
                'receipt-date': 'Date:',
                'receipt-customer': 'Customer:',
                'receipt-items-title': 'Items:',
                'receipt-total-label': 'Total:',
                'activation-title': 'Activation Instructions:',
                'activation-text': 'Your game keys are now available in "My Library". You can also find them below:',
                'view-library-btn': 'View in Library',
                'return-home-btn': 'Return to Home',
                
                // Footer
                'footer-brand': 'GAMEHUB',
                'footer-desc': 'Your premier destination for digital games and subscriptions.',
                'footer-links-title': 'Quick Links',
                'footer-home-link': 'Home',
                'footer-games-link': 'Games',
                'footer-library-link': 'My Library',
                'footer-support-title': 'Support',
                'footer-email': 'Email: support@gamehub.com',
                'footer-phone': 'Phone: +1 (555) 123-4567',
                'footer-copyright': '© 2024 GameHub. All rights reserved.',
                
                // Common
                'remove': 'Remove',
                'edit': 'Edit',
                'delete': 'Delete',
                'save': 'Save',
                'cancel': 'Cancel',
                'view': 'View',
                'ban': 'Ban',
                'unban': 'Unban',
                'download': 'Download',
                'copy': 'Copy',
                'redeem': 'Redeem'
            },
            ar: {
                // Navigation
                'nav-home': 'الرئيسية',
                'nav-games': 'الألعاب',
                'nav-library': 'مكتبتي',
                'nav-admin': 'لوحة التحكم',
                'nav-language': 'اللغة',
                'nav-currency': 'العملة',
                'nav-cart': 'عربة التسوق',
                'nav-account': 'الحساب',
                'menu-login': 'تسجيل الدخول',
                'menu-register': 'تسجيل',
                'menu-profile': 'الملف الشخصي',
                'menu-wishlist': 'قائمة الرغبات',
                'menu-logout': 'تسجيل الخروج',
                
                // Home Page
                'hero-title': 'اكتشف لعبتك المفضلة القادمة',
                'hero-subtitle': 'استكشف الآلاف من الألعاب عبر جميع الأنواع. من مغامرات مليئة بالإثارة إلى الألغاز المحيرة للعقل.',
                'hero-browse-btn': 'تصفح الألعاب',
                'hero-deals-btn': 'عرض العروض',
                'featured-badge-text': 'مميز',
                'featured-games-title': 'الألعاب المميزة',
                'filter-all': 'الكل',
                'filter-fps': 'إطلاق النار',
                'filter-rpg': 'أدوار',
                'categories-title': 'تصفح الفئات',
                'cat-fps': 'إطلاق النار',
                'cat-rpg': 'أدوار',
                'cat-action': 'مغامرة-حركة',
                'cat-mmo': 'متعددة اللاعبين',
                'cat-arcade': 'أركيد',
                'cat-racing': 'سباق',
                'cat-sports': 'رياضة',
                'cat-strategy': 'إستراتيجية',
                
                // Games Page
                'all-games-title': 'تصفح جميع الألعاب',
                'filter-all-games': 'جميع الألعاب',
                'filter-games-fps': 'إطلاق النار',
                'filter-games-rpg': 'أدوار',
                'filter-games-action': 'مغامرة-حركة',
                'filter-games-mmo': 'متعددة اللاعبين',
                'filter-games-arcade': 'أركيد',
                'filter-games-racing': 'سباق',
                'filter-games-sports': 'رياضة',
                'filter-games-strategy': 'إستراتيجية',
                'filter-games-horror': 'رعب',
                'search-btn': 'بحث',
                
                // Library Page
                'library-title': 'مكتبتي',
                'library-login-title': 'الوصول إلى الألعاب المشتراة والرموز الرقمية',
                'library-login-subtitle': 'أدخل بريدك الإلكتروني لعرض مكتبتك',
                'library-view-btn': 'عرض المكتبة',
                'library-back-btn': 'رجوع',
                'library-games-title': 'الألعاب المشتراة',
                'library-codes-title': 'الرموز الرقمية والاشتراكات',
                'library-history-title': 'سجل المشتريات',
                'history-date': 'التاريخ',
                'history-item': 'العنصر',
                'history-type': 'النوع',
                'history-price': 'السعر',
                'history-status': 'الحالة',
                
                // Admin Panel
                'admin-title': 'لوحة التحكم',
                'admin-analytics-btn': 'تحليلات المبيعات',
                'admin-games-btn': 'إدارة الألعاب',
                'admin-users-btn': 'إدارة المستخدمين',
                'admin-settings-btn': 'الإعدادات',
                'sales-chart-title': 'نظرة عامة على المبيعات (آخر 30 يومًا)',
                'stats-title': 'إحصائيات سريعة',
                'total-revenue': 'إجمالي الإيرادات',
                'total-orders': 'إجمالي الطلبات',
                'total-users': 'إجمالي المستخدمين',
                'total-games': 'إجمالي الألعاب',
                'top-games-title': 'أكثر الألعاب مبيعًا',
                'top-game': 'اللعبة',
                'top-sales': 'المبيعات',
                'top-revenue': 'الإيرادات',
                'recent-orders-title': 'الطلبات الحديثة',
                'order-id': 'رقم الطلب',
                'order-user': 'المستخدم',
                'order-amount': 'المبلغ',
                'order-date': 'التاريخ',
                'add-game-title': 'إضافة لعبة جديدة',
                'game-title-label': 'اسم اللعبة',
                'game-category-label': 'الفئة',
                'game-price-label': 'السعر (دولار)',
                'game-image-label': 'رابط الصورة',
                'game-description-label': 'الوصف',
                'game-developer-label': 'المطور',
                'add-game-btn': 'إضافة لعبة',
                'manage-games-title': 'إدارة الألعاب الحالية',
                'game-id': 'الرقم',
                'game-name': 'اسم اللعبة',
                'game-cat': 'الفئة',
                'game-price-col': 'السعر',
                'game-actions': 'الإجراءات',
                'user-management-title': 'إدارة المستخدمين',
                'user-id': 'الرقم',
                'user-email': 'البريد الإلكتروني',
                'user-name': 'الاسم',
                'user-join-date': 'تاريخ الانضمام',
                'user-orders': 'الطلبات',
                'user-total': 'إجمالي الإنفاق',
                'user-status': 'الحالة',
                'user-action': 'الإجراءات',
                'site-settings-title': 'إعدادات الموقع',
                'site-name-label': 'اسم الموقع',
                'default-currency-label': 'العملة الافتراضية',
                'default-language-label': 'اللغة الافتراضية',
                'tax-rate-label': 'معدل الضريبة (%)',
                'maintenance-label': 'وضع الصيانة',
                'save-settings-btn': 'حفظ الإعدادات',
                'payment-settings-title': 'إعدادات الدفع',
                'payment-methods-label': 'طرق الدفع الممكنة',
                'paypal-label': 'PayPal',
                'credit-card-label': 'بطاقة ائتمان/خصم',
                'crypto-label': 'عملة مشفرة',
                'min-purchase-label': 'الحد الأدنى للشراء',
                'currency-rate-label': 'سعر الدولار إلى الريال',
                'save-payment-btn': 'حفظ إعدادات الدفع',
                
                // Login/Register
                'login-title': 'تسجيل الدخول',
                'login-email-label': 'البريد الإلكتروني',
                'login-password-label': 'كلمة المرور',
                'login-submit-btn': 'تسجيل الدخول',
                'login-register-link': 'ليس لديك حساب؟ سجل الآن',
                'register-title': 'تسجيل',
                'register-name-label': 'الاسم الكامل',
                'register-email-label': 'البريد الإلكتروني',
                'register-password-label': 'كلمة المرور',
                'register-confirm-label': 'تأكيد كلمة المرور',
                'register-submit-btn': 'إنشاء حساب',
                'register-login-link': 'لديك حساب بالفعل؟ سجل الدخول',
                
                // Profile
                'profile-title': 'ملفي الشخصي',
                'profile-edit-btn': 'تعديل الملف',
                'profile-info-title': 'معلومات الحساب',
                'profile-fullname-label': 'الاسم الكامل:',
                'profile-email-label': 'البريد الإلكتروني:',
                'profile-joindate-label': 'تاريخ الانضمام:',
                'profile-totalorders-label': 'إجمالي الطلبات:',
                'profile-order-history': 'سجل الطلبات',
                'profile-order-id': 'رقم الطلب',
                'profile-order-date': 'التاريخ',
                'profile-order-items': 'العناصر',
                'profile-order-total': 'الإجمالي',
                'profile-order-status': 'الحالة',
                
                // Wishlist
                'wishlist-title': 'قائمة رغباتي',
                'wishlist-empty-title': 'قائمة رغباتك فارغة',
                'wishlist-empty-text': 'أضف ألعابًا إلى قائمة رغباتك بالنقر على أيقونة القلب في أي لعبة',
                'wishlist-browse-btn': 'تصفح الألعاب',
                
                // Cart
                'cart-title': 'عربة التسوق',
                'cart-empty-title': 'عربة التسوق فارغة',
                'cart-empty-text': 'أضف ألعابًا إلى عربة التسوق للبدء',
                'cart-browse-btn': 'تصفح الألعاب',
                'cart-summary-title': 'ملخص الطلب',
                'cart-subtotal-label': 'المجموع الفرعي:',
                'cart-tax-label': 'الضريبة (15%):',
                'cart-total-label': 'الإجمالي:',
                'cart-checkout-btn': 'المتابعة للدفع',
                'cart-continue-btn': 'مواصلة التسوق',
                
                // Checkout
                'checkout-title': 'الدفع',
                'payment-details-title': 'تفاصيل الدفع',
                'card-number-label': 'رقم البطاقة',
                'card-number-hint': 'مطلوب 12-16 رقمًا',
                'expiry-date-label': 'تاريخ الانتهاء',
                'cvv-label': 'CVV',
                'card-name-label': 'اسم حامل البطاقة',
                'payment-submit-btn': 'إتمام الدفع',
                'card-preview-title': 'بطاقة الدفع الافتراضية',
                'order-summary-title': 'ملخص الطلب',
                'order-total-label': 'الإجمالي:',
                
                // Payment Success
                'payment-success-title': 'تم الدفع بنجاح!',
                'payment-success-text': 'شكرًا لك على شرائك. تم تأكيد طلبك.',
                'receipt-title': 'GAMEHUB - إيصال رقمي',
                'receipt-order-id': 'رقم الطلب:',
                'receipt-date': 'التاريخ:',
                'receipt-customer': 'العميل:',
                'receipt-items-title': 'العناصر:',
                'receipt-total-label': 'الإجمالي:',
                'activation-title': 'تعليمات التفعيل:',
                'activation-text': 'رموز ألعابك متاحة الآن في "مكتبتي". يمكنك أيضًا العثور عليها أدناه:',
                'view-library-btn': 'عرض في المكتبة',
                'return-home-btn': 'العودة للرئيسية',
                
                // Footer
                'footer-brand': 'GAMEHUB',
                'footer-desc': 'وجهتك المميزة للألعاب الرقمية والاشتراكات.',
                'footer-links-title': 'روابط سريعة',
                'footer-home-link': 'الرئيسية',
                'footer-games-link': 'الألعاب',
                'footer-library-link': 'مكتبتي',
                'footer-support-title': 'الدعم',
                'footer-email': 'البريد الإلكتروني: support@gamehub.com',
                'footer-phone': 'الهاتف: +1 (555) 123-4567',
                'footer-copyright': '© 2024 GameHub. جميع الحقوق محفوظة.',
                
                // Common
                'remove': 'إزالة',
                'edit': 'تعديل',
                'delete': 'حذف',
                'save': 'حفظ',
                'cancel': 'إلغاء',
                'view': 'عرض',
                'ban': 'حظر',
                'unban': 'إلغاء الحظر',
                'download': 'تحميل',
                'copy': 'نسخ',
                'redeem': 'استبدال'
            }
        };

        // ===================== DATABASE INITIALIZATION =====================
        // Comprehensive game database with 60+ real games
        const gameDatabase = [
            // FPS Games (25 games)
            {
                id: 1,
                title: "Counter-Strike 2",
                category: "FPS",
                priceUSD: 0.00,
                priceSAR: 0.00,
                imageUrl: "https://cdn.cloudflare.steamstatic.com/steam/apps/730/header.jpg",
                description: "Counter-Strike 2 is the largest technical leap forward in Counter-Strike's history, ensuring new features and updates for years to come.",
                releaseDate: "August 21, 2012",
                developer: "Valve",
                publisher: "Valve",
                rating: "9/10",
                platforms: ["Windows"],
                tags: ["Multiplayer", "Competitive", "Tactical"]
            },
            {
                id: 2,
                title: "Call of Duty: Modern Warfare II",
                category: "FPS",
                priceUSD: 69.99,
                priceSAR: 262.46,
                imageUrl: "https://cdn.cloudflare.steamstatic.com/steam/apps/1938090/header.jpg",
                description: "Call of Duty®: Modern Warfare® II drops players into an unprecedented global conflict that features the return of the iconic Operators of Task Force 141.",
                releaseDate: "October 28, 2022",
                developer: "Infinity Ward",
                publisher: "Activision",
                rating: "8/10",
                platforms: ["Windows", "PlayStation", "Xbox"],
                tags: ["Campaign", "Multiplayer", "Spec Ops"]
            },
            // ... (rest of game database remains the same as before)
            // For brevity, I'll include a few sample games
            {
                id: 41,
                title: "Grand Theft Auto V: Premium Edition & Whale Shark Card Bundle",
                category: "Action-Adventure",
                priceUSD: 59.99,
                priceSAR: 225.00,
                imageUrl: "https://cdn.cloudflare.steamstatic.com/steam/apps/271590/header.jpg",
                description: "Get the complete Grand Theft Auto V experience: the award-winning Story Mode and the massive, evolving world of Grand Theft Auto Online. This bundle includes the Criminal Enterprise Starter Pack (properties, vehicles, and weapons) plus the valuable Whale Shark Cash Card to instantly add GTA$3,500,000 to your online bank account, allowing you to instantly buy the best cars, weapons, and businesses.",
                releaseDate: "April 20, 2018",
                developer: "Rockstar North",
                publisher: "Rockstar Games",
                rating: "10/10",
                platforms: ["Windows", "PlayStation", "Xbox"],
                tags: ["Open World", "Crime", "Multiplayer", "Virtual Currency"]
            },
            {
                id: 42,
                title: "Red Dead Redemption 2",
                category: "Action-Adventure",
                priceUSD: 59.99,
                priceSAR: 225.00,
                imageUrl: "https://cdn.cloudflare.steamstatic.com/steam/apps/1174180/header.jpg",
                description: "Winner of over 175 Game of the Year Awards and recipient of over 250 perfect scores, Red Dead Redemption 2 is an epic tale of honor and loyalty at the dawn of the modern age.",
                releaseDate: "November 5, 2019",
                developer: "Rockstar Studios",
                publisher: "Rockstar Games",
                rating: "10/10",
                platforms: ["Windows", "PlayStation", "Xbox"],
                tags: ["Western", "Open World", "Story Rich"]
            }
        ];

        // Digital codes/subscriptions database
        const digitalCodesDatabase = [
            {
                id: 101,
                title: "Xbox Game Pass Ultimate - 1 Month",
                category: "Subscription",
                priceUSD: 16.99,
                priceSAR: 63.75,
                imageUrl: "https://compass-ssl.xbox.com/assets/17/6c/176c6d0a-f5a1-4d5d-8b0a-5b6c6e210a1b.jpg?n=XGP_GLP-Page-Hero-0_1083x1222_02.jpg",
                description: "Xbox Game Pass Ultimate includes over 100 high-quality games for console, PC, and Android mobile devices, online multiplayer, and an EA Play membership for one low monthly price.",
                type: "Subscription",
                duration: "1 Month",
                platform: "Xbox/PC"
            },
            {
                id: 102,
                title: "Xbox Game Pass Ultimate - 3 Months",
                category: "Subscription",
                priceUSD: 44.99,
                priceSAR: 168.75,
                imageUrl: "https://compass-ssl.xbox.com/assets/17/6c/176c6d0a-f5a1-4d5d-8b0a-5b6c6e210a1b.jpg?n=XGP_GLP-Page-Hero-0_1083x1222_02.jpg",
                description: "Xbox Game Pass Ultimate 3-month subscription. Play hundreds of high-quality games on console, PC, and cloud.",
                type: "Subscription",
                duration: "3 Months",
                platform: "Xbox/PC"
            }
        ];

        // Initialize local storage
        function initializeStorage() {
            if (!localStorage.getItem('users')) {
                const defaultUsers = [
                    {
                        id: 1,
                        email: "demo@gamehub.com",
                        password: "demo123",
                        name: "Demo User",
                        joinDate: "2023-01-15",
                        isAdmin: false,
                        isBanned: false,
                        orders: [
                            {
                                orderId: "ORD123456",
                                date: "2024-01-15",
                                items: [1, 41, 26],
                                total: 375.00,
                                status: "Completed"
                            }
                        ],
                        wishlist: [1, 41, 26]
                    },
                    {
                        id: 2,
                        email: "Admin@gamehub.com",
                        password: "Admin@30",
                        name: "Admin User",
                        joinDate: "2023-01-01",
                        isAdmin: true,
                        isBanned: false,
                        orders: [],
                        wishlist: []
                    }
                ];
                localStorage.setItem('users', JSON.stringify(defaultUsers));
            }

            if (!localStorage.getItem('games')) {
                localStorage.setItem('games', JSON.stringify(gameDatabase));
            }

            if (!localStorage.getItem('digitalCodes')) {
                localStorage.setItem('digitalCodes', JSON.stringify(digitalCodesDatabase));
            }

            if (!localStorage.getItem('orders')) {
                localStorage.setItem('orders', JSON.stringify([]));
            }

            if (!localStorage.getItem('settings')) {
                const defaultSettings = {
                    siteName: "GAMEHUB",
                    defaultCurrency: "USD",
                    defaultLanguage: "en",
                    taxRate: 15,
                    maintenanceMode: false,
                    paymentMethods: {
                        paypal: true,
                        creditCard: true,
                        crypto: false
                    },
                    minPurchase: 5.00,
                    currencyRate: 3.75
                };
                localStorage.setItem('settings', JSON.stringify(defaultSettings));
            }
        }

        // ===================== USER MANAGEMENT =====================
        function getCurrentUser() {
            const userEmail = localStorage.getItem('currentUser');
            if (!userEmail) return null;
            
            const users = JSON.parse(localStorage.getItem('users'));
            return users.find(user => user.email === userEmail);
        }

        function saveUser(user) {
            const users = JSON.parse(localStorage.getItem('users'));
            const index = users.findIndex(u => u.email === user.email);
            if (index !== -1) {
                users[index] = user;
                localStorage.setItem('users', JSON.stringify(users));
            }
        }

        function loginUser(email, password) {
            // Try backend first
            fetch(`${API_BASE_URL}/auth/login`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ email, password })
            })
            .then(response => response.json())
            .then(data => {
                if (data.success && data.token) {
                    authToken = data.token;
                    localStorage.setItem('authToken', authToken);
                    localStorage.setItem('currentUser', email);
                    currentUser = data.user;
                    updateUIForUser();
                    return { success: true, user: data.user };
                } else {
                    // Fallback to localStorage
                    const users = JSON.parse(localStorage.getItem('users'));
                    const user = users.find(u => u.email === email && u.password === password);
                    
                    if (user) {
                        if (user.isBanned) {
                            return { success: false, message: "Account is banned. Contact support." };
                        }
                        
                        localStorage.setItem('currentUser', user.email);
                        currentUser = user;
                        updateUIForUser();
                        return { success: true, user: user };
                    }
                    return { success: false, message: data.message || "Invalid credentials" };
                }
            })
            .catch(err => {
                // Fallback to localStorage if backend is not available
                const users = JSON.parse(localStorage.getItem('users'));
                const user = users.find(u => u.email === email && u.password === password);
                
                if (user) {
                    if (user.isBanned) {
                        alert("Account is banned. Contact support.");
                        return { success: false, message: "Account is banned" };
                    }
                    
                    localStorage.setItem('currentUser', user.email);
                    currentUser = user;
                    updateUIForUser();
                    return { success: true, user: user };
                }
                
                alert("Invalid email or password");
                return { success: false, message: "Invalid email or password" };
            });
        }

        function registerUser(name, email, password) {
            // Try backend first
            fetch(`${API_BASE_URL}/auth/register`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ name, email, password })
            })
            .then(response => response.json())
            .then(data => {
                if (data.success && data.token) {
                    authToken = data.token;
                    localStorage.setItem('authToken', authToken);
                    localStorage.setItem('currentUser', email);
                    currentUser = data.user;
                    updateUIForUser();
                    return { success: true, user: data.user };
                } else {
                    // Fallback to localStorage
                    const users = JSON.parse(localStorage.getItem('users'));
                    
                    if (users.some(u => u.email === email)) {
                        return { success: false, message: "Email already registered" };
                    }
                    
                    const newUser = {
                        id: users.length + 1,
                        email: email,
                        password: password,
                        name: name,
                        joinDate: new Date().toISOString().split('T')[0],
                        isAdmin: false,
                        isBanned: false,
                        orders: [],
                        wishlist: []
                    };
                    
                    users.push(newUser);
                    localStorage.setItem('users', JSON.stringify(users));
                    
                    localStorage.setItem('currentUser', email);
                    currentUser = newUser;
                    updateUIForUser();
                    
                    return { success: true, user: newUser };
                }
            })
            .catch(err => {
                // Fallback to localStorage
                const users = JSON.parse(localStorage.getItem('users'));
                
                if (users.some(u => u.email === email)) {
                    alert("Email already registered");
                    return { success: false, message: "Email already registered" };
                }
                
                const newUser = {
                    id: users.length + 1,
                    email: email,
                    password: password,
                    name: name,
                    joinDate: new Date().toISOString().split('T')[0],
                    isAdmin: false,
                    isBanned: false,
                    orders: [],
                    wishlist: []
                };
                
                users.push(newUser);
                localStorage.setItem('users', JSON.stringify(users));
                
                localStorage.setItem('currentUser', email);
                currentUser = newUser;
                updateUIForUser();
                
                return { success: true, user: newUser };
            });
        }

        function logout() {
            localStorage.removeItem('currentUser');
            currentUser = null;
            updateUIForUser();
            showPage('home');
            alert(getTranslation('menu-logout') + ' successful!');
        }

        // ===================== CURRENCY MANAGEMENT =====================
        function changeCurrency(currency) {
            currentCurrency = currency;
            localStorage.setItem('currentCurrency', currency);
            updateAllPrices();
            updateCurrencyDropdown();
        }

        function getPriceInCurrentCurrency(priceUSD) {
            if (currentCurrency === 'SAR') {
                return (priceUSD * conversionRate).toFixed(2);
            }
            return priceUSD.toFixed(2);
        }

        function getCurrencySymbol() {
            return currentCurrency === 'USD' ? '$' : 'ر.س';
        }

        function updateAllPrices() {
            // Update prices in games page
            if (document.getElementById('all-games-container')) {
                loadAllGames();
            }
            
            // Update prices in cart
            if (document.getElementById('cart-content')) {
                updateCartDisplay();
            }
            
            // Update currency dropdown
            updateCurrencyDropdown();
        }

        function updateCurrencyDropdown() {
            const currencyDropdown = document.getElementById('currencyDropdown');
            if (currencyDropdown) {
                currencyDropdown.innerHTML = `<i class="fas fa-money-bill-wave me-1"></i>${getTranslation('nav-currency')} (${getCurrencySymbol()})`;
            }
        }

        // ===================== LANGUAGE MANAGEMENT =====================
        function changeLanguage(lang) {
            currentLanguage = lang;
            localStorage.setItem('currentLanguage', lang);
            
            // Update HTML direction for Arabic
            if (lang === 'ar') {
                document.body.dir = 'rtl';
                document.getElementById('html-lang').lang = 'ar';
            } else {
                document.body.dir = 'ltr';
                document.getElementById('html-lang').lang = 'en';
            }
            
            applyTranslations();
        }

        function getTranslation(key) {
            return translations[currentLanguage][key] || translations['en'][key] || key;
        }

        function applyTranslations() {
            // Apply translations to all elements with translation IDs
            Object.keys(translations[currentLanguage]).forEach(key => {
                const elements = document.querySelectorAll(`[id="${key}"]`);
                elements.forEach(element => {
                    if (element.tagName === 'INPUT' || element.tagName === 'TEXTAREA') {
                        element.placeholder = translations[currentLanguage][key];
                    } else {
                        element.textContent = translations[currentLanguage][key];
                    }
                });
            });
            
            // Update dropdown labels
            updateCurrencyDropdown();
        }

        // ===================== CART MANAGEMENT =====================
        function addToCart(gameId) {
            if (!currentUser) {
                alert("Please login first to add items to cart");
                showPage('login');
                return;
            }
            
            const game = gameDatabase.find(g => g.id === gameId);
            if (!game) return;
            
            const cartItem = {
                id: gameId,
                title: game.title,
                priceUSD: game.priceUSD,
                quantity: 1
            };
            
            cart.push(cartItem);
            updateCartCount();
            saveCartToStorage();
            
            alert(`${game.title} added to cart!`);
        }

        function updateCartCount() {
            const count = cart.reduce((total, item) => total + item.quantity, 0);
            document.getElementById('cart-count').textContent = count;
        }

        function saveCartToStorage() {
            if (currentUser) {
                localStorage.setItem(`cart_${currentUser.email}`, JSON.stringify(cart));
            }
        }

        function loadCartFromStorage() {
            if (currentUser) {
                const savedCart = localStorage.getItem(`cart_${currentUser.email}`);
                cart = savedCart ? JSON.parse(savedCart) : [];
            } else {
                cart = [];
            }
            updateCartCount();
        }

        function updateCartDisplay() {
            const cartItemsList = document.getElementById('cart-items-list');
            const cartEmpty = document.getElementById('cart-empty');
            const cartContent = document.getElementById('cart-content');
            
            if (!cartItemsList) return;
            
            if (cart.length === 0) {
                cartEmpty.style.display = 'block';
                cartContent.style.display = 'none';
                return;
            }
            
            cartEmpty.style.display = 'none';
            cartContent.style.display = 'block';
            
            let html = '';
            let subtotal = 0;
            
            cart.forEach(item => {
                const price = currentCurrency === 'USD' ? item.priceUSD : (item.priceUSD * conversionRate);
                const itemTotal = price * item.quantity;
                subtotal += itemTotal;
                
                html += `
                    <div class="cart-item">
                        <div class="row align-items-center">
                            <div class="col-md-2">
                                <img src="${gameDatabase.find(g => g.id === item.id)?.imageUrl || 'https://placehold.co/100x100/1a1a2e/fff?text=Game'}" 
                                     alt="${item.title}" class="img-fluid rounded">
                            </div>
                            <div class="col-md-4">
                                <h6>${item.title}</h6>
                                <small class="text-muted">${gameDatabase.find(g => g.id === item.id)?.category || 'Game'}</small>
                            </div>
                            <div class="col-md-2">
                                <span class="game-price">${getCurrencySymbol()}${getPriceInCurrentCurrency(item.priceUSD)}</span>
                            </div>
                            <div class="col-md-2">
                                <input type="number" class="form-control form-control-sm" 
                                       value="${item.quantity}" min="1" 
                                       onchange="updateCartQuantity(${item.id}, this.value)">
                            </div>
                            <div class="col-md-2 text-end">
                                <button class="btn btn-outline-danger btn-sm" onclick="removeFromCart(${item.id})">
                                    <i class="fas fa-trash"></i>
                                </button>
                            </div>
                        </div>
                    </div>
                `;
            });
            
            cartItemsList.innerHTML = html;
            
            const tax = subtotal * 0.15;
            const total = subtotal + tax;
            
            document.getElementById('cart-subtotal').textContent = `${getCurrencySymbol()}${subtotal.toFixed(2)}`;
            document.getElementById('cart-tax').textContent = `${getCurrencySymbol()}${tax.toFixed(2)}`;
            document.getElementById('cart-total').textContent = `${getCurrencySymbol()}${total.toFixed(2)}`;
        }

        function updateCartQuantity(gameId, quantity) {
            const item = cart.find(item => item.id === gameId);
            if (item) {
                item.quantity = parseInt(quantity) || 1;
                saveCartToStorage();
                updateCartDisplay();
            }
        }

        function removeFromCart(gameId) {
            cart = cart.filter(item => item.id !== gameId);
            saveCartToStorage();
            updateCartDisplay();
            updateCartCount();
        }

        function proceedToCheckout() {
            if (cart.length === 0) {
                alert("Your cart is empty!");
                return;
            }
            
            showPage('checkout');
            updateCheckoutDisplay();
        }

        function updateCheckoutDisplay() {
            const checkoutItems = document.getElementById('checkout-items');
            let html = '';
            let subtotal = 0;
            
            cart.forEach(item => {
                const price = currentCurrency === 'USD' ? item.priceUSD : (item.priceUSD * conversionRate);
                const itemTotal = price * item.quantity;
                subtotal += itemTotal;
                
                html += `
                    <div class="d-flex justify-content-between mb-2">
                        <span>${item.title} x${item.quantity}</span>
                        <span>${getCurrencySymbol()}${itemTotal.toFixed(2)}</span>
                    </div>
                `;
            });
            
            checkoutItems.innerHTML = html;
            
            const tax = subtotal * 0.15;
            const total = subtotal + tax;
            
            document.getElementById('order-total').textContent = `${getCurrencySymbol()}${total.toFixed(2)}`;
            
            // Update card preview
            updateCardPreview();
        }

        // ===================== PAYMENT PROCESSING =====================
        function processPayment() {
            const cardNumber = document.getElementById('card-number').value.replace(/\s/g, '');
            const expiryDate = document.getElementById('expiry-date').value;
            const cvv = document.getElementById('cvv').value;
            const cardName = document.getElementById('card-name').value;
            
            // Validation
            if (!validateCardNumber(cardNumber)) {
                alert("Invalid card number. Must be 12-16 digits.");
                return false;
            }
            
            if (!validateExpiryDate(expiryDate)) {
                alert("Invalid expiry date. Must be in MM/YY format and not expired.");
                return false;
            }
            
            if (!validateCVV(cvv)) {
                alert("Invalid CVV. Must be 3 digits.");
                return false;
            }
            
            if (!validateCardName(cardName)) {
                alert("Invalid cardholder name. Must be 12-36 characters.");
                return false;
            }
            
            return true;
        }

        function validateCardNumber(number) {
            return /^\d{12,16}$/.test(number);
        }

        function validateExpiryDate(date) {
            if (!/^\d{2}\/\d{2}$/.test(date)) return false;
            
            const [month, year] = date.split('/').map(Number);
            const currentYear = new Date().getFullYear() % 100;
            const currentMonth = new Date().getMonth() + 1;
            
            if (month < 1 || month > 12) return false;
            if (year < currentYear) return false;
            if (year === currentYear && month < currentMonth) return false;
            
            return true;
        }

        function validateCVV(cvv) {
            return /^\d{3}$/.test(cvv);
        }

        function validateCardName(name) {
            return name.length >= 12 && name.length <= 36;
        }

        function completePayment() {
            if (!processPayment()) return;
            
            // Generate order
            const orderId = 'ORD' + Math.random().toString(36).substr(2, 9).toUpperCase();
            const orderDate = new Date().toISOString().split('T')[0];
            
            let subtotal = 0;
            cart.forEach(item => {
                const price = currentCurrency === 'USD' ? item.priceUSD : (item.priceUSD * conversionRate);
                subtotal += price * item.quantity;
            });
            
            const tax = subtotal * 0.15;
            const total = subtotal + tax;
            
            // Create order object
            const order = {
                orderId: orderId,
                date: orderDate,
                items: cart.map(item => item.id),
                gameKeys: cart.map(item => generateGameKey(item.title)),
                total: total,
                currency: currentCurrency,
                status: "Completed"
            };
            
            // Save order to user
            if (currentUser) {
                currentUser.orders.push(order);
                saveUser(currentUser);
                
                // Save to global orders
                const orders = JSON.parse(localStorage.getItem('orders'));
                orders.push({
                    ...order,
                    userId: currentUser.id,
                    userEmail: currentUser.email
                });
                localStorage.setItem('orders', JSON.stringify(orders));
            }
            
            // Clear cart
            cart = [];
            saveCartToStorage();
            updateCartCount();
            
            // Show success page
            showPaymentSuccess(order);
        }

        function showPaymentSuccess(order) {
            showPage('payment-success');
            
            // Update receipt
            document.getElementById('receipt-order-id-value').textContent = order.orderId;
            document.getElementById('receipt-date-value').textContent = new Date(order.date).toLocaleDateString();
            document.getElementById('receipt-customer-value').textContent = currentUser?.name || 'Guest';
            
            let receiptItemsHtml = '';
            let receiptCodesHtml = '';
            
            order.items.forEach((gameId, index) => {
                const game = gameDatabase.find(g => g.id === gameId);
                if (game) {
                    receiptItemsHtml += `
                        <div class="d-flex justify-content-between">
                            <span>${game.title}</span>
                            <span>${getCurrencySymbol()}${(currentCurrency === 'USD' ? game.priceUSD : game.priceUSD * conversionRate).toFixed(2)}</span>
                        </div>
                    `;
                    
                    receiptCodesHtml += `
                        <div class="mb-2">
                            <strong>${game.title}:</strong>
                            <div class="game-code">
                                <code>${order.gameKeys[index]}</code>
                                <button onclick="copyToClipboard('${order.gameKeys[index]}')" class="copy-btn">${getTranslation('copy')}</button>
                            </div>
                        </div>
                    `;
                }
            });
            
            document.getElementById('receipt-items').innerHTML = receiptItemsHtml;
            document.getElementById('receipt-game-codes').innerHTML = receiptCodesHtml;
            document.getElementById('receipt-total-value').textContent = `${getCurrencySymbol()}${order.total.toFixed(2)}`;
        }

        // ===================== WISHLIST MANAGEMENT =====================
        function toggleWishlist(gameId) {
            if (!currentUser) {
                alert("Please login to use wishlist");
                showPage('login');
                return;
            }
            
            const index = currentUser.wishlist.indexOf(gameId);
            if (index === -1) {
                currentUser.wishlist.push(gameId);
                alert("Added to wishlist!");
            } else {
                currentUser.wishlist.splice(index, 1);
                alert("Removed from wishlist!");
            }
            
            saveUser(currentUser);
            updateWishlistDisplay();
        }

        function isInWishlist(gameId) {
            return currentUser?.wishlist?.includes(gameId) || false;
        }

        function updateWishlistDisplay() {
            const wishlistGames = document.getElementById('wishlist-games');
            const wishlistEmpty = document.getElementById('wishlist-empty');
            
            if (!currentUser || currentUser.wishlist.length === 0) {
                wishlistEmpty.style.display = 'block';
                wishlistGames.innerHTML = '';
                return;
            }
            
            wishlistEmpty.style.display = 'none';
            
            let html = '';
            currentUser.wishlist.forEach(gameId => {
                const game = gameDatabase.find(g => g.id === gameId);
                if (!game) return;
                
                html += `
                    <div class="col-md-4 col-lg-3">
                        <div class="game-card h-100">
                            <div class="position-relative">
                                <span class="featured-badge">${game.category}</span>
                                <img src="${game.imageUrl}" alt="${game.title}" onerror="this.src='https://placehold.co/400x200/1a1a2e/fff?text=${encodeURIComponent(game.title)}'">
                            </div>
                            <div class="card-body d-flex flex-column">
                                <h5 class="game-title">${game.title}</h5>
                                <p class="text-muted small mb-2">${game.category} • ${game.developer}</p>
                                <div class="mt-auto d-flex justify-content-between align-items-center">
                                    <span class="game-price">${getCurrencySymbol()}${getPriceInCurrentCurrency(game.priceUSD)}</span>
                                    <div>
                                        <button onclick="addToCart(${game.id})" class="btn btn-game btn-sm me-1">Buy</button>
                                        <button onclick="toggleWishlist(${game.id})" class="btn btn-outline-danger btn-sm">
                                            <i class="fas fa-heart"></i>
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                `;
            });
            
            wishlistGames.innerHTML = html;
        }

        // ===================== ADMIN FUNCTIONS =====================
        function adminLogin() {
            const email = document.getElementById('admin-email').value;
            const password = document.getElementById('admin-password').value;
            
            const result = loginUser(email, password);
            if (result.success && result.user.isAdmin) {
                document.getElementById('admin-login-check').style.display = 'none';
                document.getElementById('admin-content').style.display = 'block';
                showAdminSection('analytics');
                updateAdminUI();
            } else {
                alert("Invalid admin credentials");
            }
        }

        function showAdminSection(section) {
            // Hide all sections
            document.querySelectorAll('.admin-section').forEach(s => {
                s.style.display = 'none';
            });
            
            // Remove active class from all nav links
            document.querySelectorAll('.admin-nav .nav-link').forEach(link => {
                link.classList.remove('active');
            });
            
            // Show selected section
            document.getElementById(`admin-${section}`).style.display = 'block';
            
            // Add active class to clicked nav link
            event.target.classList.add('active');
        }

        function updateAdminUI() {
            if (!currentUser?.isAdmin) return;
            
            // Load analytics
            loadAnalytics();
            
            // Load games management
            loadGamesManagement();
            
            // Load users management
            loadUsersManagement();
            
            // Load settings
            loadSettings();
        }

        function loadAnalytics() {
            const orders = JSON.parse(localStorage.getItem('orders')) || [];
            const users = JSON.parse(localStorage.getItem('users')) || [];
            const games = JSON.parse(localStorage.getItem('games')) || [];
            
            // Update stats
            let totalRevenue = 0;
            orders.forEach(order => {
                totalRevenue += order.total;
            });
            
            document.getElementById('total-revenue-amount').textContent = `$${totalRevenue.toFixed(2)}`;
            document.getElementById('total-orders-count').textContent = orders.length;
            document.getElementById('total-users-count').textContent = users.length;
            document.getElementById('total-games-count').textContent = games.length;
            
            // Load top games
            const gameSales = {};
            orders.forEach(order => {
                order.items.forEach(gameId => {
                    gameSales[gameId] = (gameSales[gameId] || 0) + 1;
                });
            });
            
            const topGamesList = document.getElementById('top-games-list');
            topGamesList.innerHTML = '';
            
            Object.entries(gameSales)
                .sort((a, b) => b[1] - a[1])
                .slice(0, 5)
                .forEach(([gameId, sales]) => {
                    const game = games.find(g => g.id == gameId);
                    if (game) {
                        const revenue = sales * game.priceUSD;
                        const row = `
                            <tr>
                                <td>${game.title}</td>
                                <td>${sales}</td>
                                <td>$${revenue.toFixed(2)}</td>
                            </tr>
                        `;
                        topGamesList.innerHTML += row;
                    }
                });
            
            // Load recent orders
            const recentOrdersList = document.getElementById('recent-orders-list');
            recentOrdersList.innerHTML = '';
            
            orders.slice(-5).reverse().forEach(order => {
                const user = users.find(u => u.email === order.userEmail);
                const row = `
                    <tr>
                        <td>${order.orderId}</td>
                        <td>${user?.name || 'Unknown'}</td>
                        <td>$${order.total.toFixed(2)}</td>
                        <td>${order.date}</td>
                    </tr>
                `;
                recentOrdersList.innerHTML += row;
            });
            
            // Create chart
            createSalesChart(orders);
        }

        function createSalesChart(orders) {
            const ctx = document.getElementById('salesChart').getContext('2d');
            
            // Group orders by date (last 30 days)
            const last30Days = [];
            for (let i = 29; i >= 0; i--) {
                const date = new Date();
                date.setDate(date.getDate() - i);
                last30Days.push(date.toISOString().split('T')[0]);
            }
            
            const dailySales = last30Days.map(date => {
                const dayOrders = orders.filter(order => order.date === date);
                return dayOrders.reduce((sum, order) => sum + order.total, 0);
            });
            
            new Chart(ctx, {
                type: 'line',
                data: {
                    labels: last30Days.map(date => date.substring(5)),
                    datasets: [{
                        label: 'Daily Revenue ($)',
                        data: dailySales,
                        borderColor: '#ff0000',
                        backgroundColor: 'rgba(255, 0, 0, 0.1)',
                        borderWidth: 2,
                        fill: true
                    }]
                },
                options: {
                    responsive: true,
                    plugins: {
                        legend: {
                            labels: {
                                color: '#fff'
                            }
                        }
                    },
                    scales: {
                        y: {
                            beginAtZero: true,
                            ticks: {
                                color: '#fff'
                            },
                            grid: {
                                color: 'rgba(255, 255, 255, 0.1)'
                            }
                        },
                        x: {
                            ticks: {
                                color: '#fff'
                            },
                            grid: {
                                color: 'rgba(255, 255, 255, 0.1)'
                            }
                        }
                    }
                }
            });
        }

        function loadGamesManagement() {
            const games = JSON.parse(localStorage.getItem('games')) || [];
            const gamesList = document.getElementById('games-management-list');
            gamesList.innerHTML = '';
            
            games.forEach(game => {
                const row = `
                    <tr>
                        <td>${game.id}</td>
                        <td>${game.title}</td>
                        <td>${game.category}</td>
                        <td>$${game.priceUSD}</td>
                        <td>
                            <button class="btn btn-sm btn-outline-warning me-1" onclick="editGame(${game.id})">
                                <i class="fas fa-edit"></i>
                            </button>
                            <button class="btn btn-sm btn-outline-danger" onclick="deleteGame(${game.id})">
                                <i class="fas fa-trash"></i>
                            </button>
                        </td>
                    </tr>
                `;
                gamesList.innerHTML += row;
            });
            
            // Add game form submission
            document.getElementById('add-game-form').onsubmit = function(e) {
                e.preventDefault();
                addNewGame();
            };
        }

        function addNewGame() {
            const title = document.getElementById('new-game-title').value;
            const category = document.getElementById('new-game-category').value;
            const priceUSD = parseFloat(document.getElementById('new-game-price').value);
            const imageUrl = document.getElementById('new-game-image').value;
            const description = document.getElementById('new-game-description').value;
            const developer = document.getElementById('new-game-developer').value;
            
            const games = JSON.parse(localStorage.getItem('games'));
            const newId = Math.max(...games.map(g => g.id)) + 1;
            
            const newGame = {
                id: newId,
                title: title,
                category: category,
                priceUSD: priceUSD,
                priceSAR: priceUSD * conversionRate,
                imageUrl: imageUrl || 'https://placehold.co/400x200/1a1a2e/fff?text=' + encodeURIComponent(title),
                description: description,
                releaseDate: new Date().toISOString().split('T')[0],
                developer: developer,
                publisher: developer,
                rating: "8/10",
                platforms: ["Windows"],
                tags: [category]
            };
            
            games.push(newGame);
            localStorage.setItem('games', JSON.stringify(games));
            
            alert("Game added successfully!");
            document.getElementById('add-game-form').reset();
            loadGamesManagement();
        }

        function deleteGame(gameId) {
            if (!confirm("Are you sure you want to delete this game?")) return;
            
            const games = JSON.parse(localStorage.getItem('games'));
            const filteredGames = games.filter(game => game.id !== gameId);
            localStorage.setItem('games', JSON.stringify(filteredGames));
            
            loadGamesManagement();
        }

        function loadUsersManagement() {
            const users = JSON.parse(localStorage.getItem('users')) || [];
            const usersList = document.getElementById('users-management-list');
            usersList.innerHTML = '';
            
            users.forEach(user => {
                const totalSpent = user.orders.reduce((sum, order) => sum + order.total, 0);
                const row = `
                    <tr class="user-row">
                        <td>${user.id}</td>
                        <td>${user.email}</td>
                        <td>${user.name}</td>
                        <td>${user.joinDate}</td>
                        <td>${user.orders.length}</td>
                        <td>$${totalSpent.toFixed(2)}</td>
                        <td>
                            ${user.isBanned ? 
                                '<span class="banned-badge">Banned</span>' : 
                                '<span class="badge bg-success">Active</span>'
                            }
                        </td>
                        <td>
                            <button class="btn btn-sm btn-outline-info me-1" onclick="viewUserDetails(${user.id})">
                                <i class="fas fa-eye"></i>
                            </button>
                            ${user.isBanned ? 
                                `<button class="btn btn-sm btn-outline-success" onclick="toggleBanUser(${user.id}, false)">
                                    <i class="fas fa-check"></i>
                                </button>` :
                                `<button class="btn btn-sm btn-outline-danger" onclick="toggleBanUser(${user.id}, true)">
                                    <i class="fas fa-ban"></i>
                                </button>`
                            }
                        </td>
                    </tr>
                `;
                usersList.innerHTML += row;
            });
        }

        function toggleBanUser(userId, ban) {
            const users = JSON.parse(localStorage.getItem('users'));
            const user = users.find(u => u.id === userId);
            
            if (user) {
                user.isBanned = ban;
                localStorage.setItem('users', JSON.stringify(users));
                
                if (ban) {
                    alert(`User ${user.email} has been banned.`);
                } else {
                    alert(`User ${user.email} has been unbanned.`);
                }
                
                loadUsersManagement();
            }
        }

        function loadSettings() {
            const settings = JSON.parse(localStorage.getItem('settings'));
            
            document.getElementById('site-name').value = settings.siteName;
            document.getElementById('default-currency').value = settings.defaultCurrency;
            document.getElementById('default-language').value = settings.defaultLanguage;
            document.getElementById('tax-rate').value = settings.taxRate;
            document.getElementById('maintenance-mode').checked = settings.maintenanceMode;
            document.getElementById('paypal-enabled').checked = settings.paymentMethods.paypal;
            document.getElementById('credit-card-enabled').checked = settings.paymentMethods.creditCard;
            document.getElementById('crypto-enabled').checked = settings.paymentMethods.crypto;
            document.getElementById('min-purchase').value = settings.minPurchase;
            document.getElementById('currency-rate').value = settings.currencyRate;
            
            // Settings form submission
            document.getElementById('site-settings-form').onsubmit = function(e) {
                e.preventDefault();
                saveSiteSettings();
            };
            
            document.getElementById('payment-settings-form').onsubmit = function(e) {
                e.preventDefault();
                savePaymentSettings();
            };
        }

        function saveSiteSettings() {
            const settings = JSON.parse(localStorage.getItem('settings'));
            
            settings.siteName = document.getElementById('site-name').value;
            settings.defaultCurrency = document.getElementById('default-currency').value;
            settings.defaultLanguage = document.getElementById('default-language').value;
            settings.taxRate = parseFloat(document.getElementById('tax-rate').value);
            settings.maintenanceMode = document.getElementById('maintenance-mode').checked;
            
            localStorage.setItem('settings', JSON.stringify(settings));
            alert("Site settings saved successfully!");
        }

        function savePaymentSettings() {
            const settings = JSON.parse(localStorage.getItem('settings'));
            
            settings.paymentMethods.paypal = document.getElementById('paypal-enabled').checked;
            settings.paymentMethods.creditCard = document.getElementById('credit-card-enabled').checked;
            settings.paymentMethods.crypto = document.getElementById('crypto-enabled').checked;
            settings.minPurchase = parseFloat(document.getElementById('min-purchase').value);
            settings.currencyRate = parseFloat(document.getElementById('currency-rate').value);
            
            // Update conversion rate
            conversionRate = settings.currencyRate;
            
            localStorage.setItem('settings', JSON.stringify(settings));
            alert("Payment settings saved successfully!");
        }

        // ===================== UI UPDATES =====================
        function updateUIForUser() {
            const loggedInOnly = document.querySelectorAll('.logged-in-only');
            const loginMenuItem = document.getElementById('login-menu-item');
            const registerMenuItem = document.getElementById('register-menu-item');
            const adminOnly = document.querySelectorAll('.admin-only');
            
            if (currentUser) {
                // User is logged in
                loggedInOnly.forEach(el => el.style.display = 'block');
                loginMenuItem.style.display = 'none';
                registerMenuItem.style.display = 'none';
                
                // Check if user is admin
                if (currentUser.isAdmin) {
                    adminOnly.forEach(el => el.style.display = 'block');
                } else {
                    adminOnly.forEach(el => el.style.display = 'none');
                }
                
                // Update cart
                loadCartFromStorage();
                updateCartCount();
                
                // Update wishlist
                updateWishlistDisplay();
            } else {
                // User is not logged in
                loggedInOnly.forEach(el => el.style.display = 'none');
                adminOnly.forEach(el => el.style.display = 'none');
                loginMenuItem.style.display = 'block';
                registerMenuItem.style.display = 'block';
                
                // Clear cart
                cart = [];
                updateCartCount();
            }
        }

        function updateCardPreview() {
            const cardNumber = document.getElementById('card-number');
            const expiryDate = document.getElementById('expiry-date');
            const cardName = document.getElementById('card-name');
            
            if (cardNumber) {
                const previewNumber = document.getElementById('card-preview-number');
                if (cardNumber.value) {
                    const last4 = cardNumber.value.replace(/\s/g, '').slice(-4);
                    previewNumber.textContent = `**** **** **** ${last4}`;
                } else {
                    previewNumber.textContent = '**** **** **** ****';
                }
            }
            
            if (expiryDate) {
                const previewExpiry = document.getElementById('card-preview-expiry');
                previewExpiry.textContent = expiryDate.value || 'MM/YY';
            }
            
            if (cardName) {
                const previewName = document.getElementById('card-preview-name');
                previewName.textContent = cardName.value.toUpperCase() || 'CARDHOLDER NAME';
            }
        }

        // ===================== PAGE NAVIGATION =====================
        function showPage(pageId) {
            const pages = document.querySelectorAll('.page-section');
            pages.forEach(page => page.classList.remove('active'));
            
            const activePage = document.getElementById(pageId);
            if (activePage) activePage.classList.add('active');
            window.scrollTo(0, 0);

            // Load appropriate content
            if (pageId === 'home') {
                loadGames('all');
                updateCategoryCounts();
            } else if (pageId === 'games') {
                loadAllGames();
                updateCategoryCounts();
            } else if (pageId === 'cart') {
                updateCartDisplay();
            } else if (pageId === 'wishlist') {
                updateWishlistDisplay();
            } else if (pageId === 'profile' && currentUser) {
                loadProfile();
            } else if (pageId === 'checkout') {
                updateCheckoutDisplay();
            } else if (pageId === 'admin') {
                if (!currentUser || !currentUser.isAdmin) {
                    // Show admin login
                    document.getElementById('admin-login-check').style.display = 'block';
                    document.getElementById('admin-content').style.display = 'none';
                } else {
                    // Show admin panel
                    document.getElementById('admin-login-check').style.display = 'none';
                    document.getElementById('admin-content').style.display = 'block';
                    showAdminSection('analytics');
                    updateAdminUI();
                }
            }
        }

        function loadProfile() {
            if (!currentUser) return;
            
            document.getElementById('profile-name').textContent = currentUser.name;
            document.getElementById('profile-email').textContent = currentUser.email;
            document.getElementById('profile-member-since').textContent = `Member since: ${currentUser.joinDate}`;
            document.getElementById('profile-fullname').textContent = currentUser.name;
            document.getElementById('profile-email-value').textContent = currentUser.email;
            document.getElementById('profile-joindate-value').textContent = currentUser.joinDate;
            document.getElementById('profile-totalorders-value').textContent = currentUser.orders.length;
            
            // Load orders
            const ordersList = document.getElementById('profile-orders-list');
            ordersList.innerHTML = '';
            
            currentUser.orders.forEach(order => {
                const row = `
                    <tr>
                        <td>${order.orderId}</td>
                        <td>${order.date}</td>
                        <td>${order.items.length} items</td>
                        <td>$${order.total.toFixed(2)}</td>
                        <td><span class="badge bg-success">${order.status}</span></td>
                    </tr>
                `;
                ordersList.innerHTML += row;
            });
        }

        // ===================== GAME DISPLAY FUNCTIONS =====================
        function loadGames(category = 'all') {
            // Try to load from backend first
            fetch(`${API_BASE_URL}/games`)
                .then(response => response.json())
                .then(data => {
                    let gamesToDisplay = category === 'all' 
                        ? data.slice(0, 8)
                        : data.filter(game => game.category === category).slice(0, 8);

                    const container = document.getElementById('games-container');
                    if (!container) return;
                    
                    container.innerHTML = '';

                    gamesToDisplay.forEach(game => {
                        const card = `
                            <div class="col-md-4 col-lg-3">
                                <div class="game-card h-100">
                                    <div class="position-relative">
                                        <span class="featured-badge">${game.category}</span>
                                        <img src="${game.imageUrl}" alt="${game.title}" onerror="this.src='https://placehold.co/400x200/1a1a2e/fff?text=${encodeURIComponent(game.title)}'">
                                    </div>
                                    <div class="card-body d-flex flex-column">
                                        <h5 class="game-title">${game.title}</h5>
                                        <p class="text-muted small mb-2">${game.category} • ${game.developer}</p>
                                        <p class="game-description small mb-2">${game.description.substring(0, 80)}...</p>
                                        <div class="mt-auto d-flex justify-content-between align-items-center">
                                            <span class="game-price">${getCurrencySymbol()}${getPriceInCurrentCurrency(game.priceUSD)}</span>
                                            <div>
                                                <button onclick="addToCart(${game.id})" class="btn btn-game btn-sm me-1">Buy</button>
                                                <button onclick="toggleWishlist(${game.id})" class="btn ${isInWishlist(game.id) ? 'btn-outline-danger' : 'btn-outline-light'} btn-sm">
                                                    <i class="fas fa-heart"></i>
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>`;
                        container.innerHTML += card;
                    });
                })
                .catch(err => {
                    // Fallback to localStorage
                    const games = JSON.parse(localStorage.getItem('games')) || gameDatabase;
                    let gamesToDisplay = category === 'all' 
                        ? games.slice(0, 8)
                        : games.filter(game => game.category === category).slice(0, 8);

                    const container = document.getElementById('games-container');
                    if (!container) return;
                    
                    container.innerHTML = '';

                    gamesToDisplay.forEach(game => {
                        const card = `
                            <div class="col-md-4 col-lg-3">
                                <div class="game-card h-100">
                                    <div class="position-relative">
                                        <span class="featured-badge">${game.category}</span>
                                        <img src="${game.imageUrl}" alt="${game.title}" onerror="this.src='https://placehold.co/400x200/1a1a2e/fff?text=${encodeURIComponent(game.title)}'">
                                    </div>
                                    <div class="card-body d-flex flex-column">
                                        <h5 class="game-title">${game.title}</h5>
                                        <p class="text-muted small mb-2">${game.category} • ${game.developer}</p>
                                        <p class="game-description small mb-2">${game.description.substring(0, 80)}...</p>
                                        <div class="mt-auto d-flex justify-content-between align-items-center">
                                            <span class="game-price">${getCurrencySymbol()}${getPriceInCurrentCurrency(game.priceUSD)}</span>
                                            <div>
                                                <button onclick="addToCart(${game.id})" class="btn btn-game btn-sm me-1">Buy</button>
                                                <button onclick="toggleWishlist(${game.id})" class="btn ${isInWishlist(game.id) ? 'btn-outline-danger' : 'btn-outline-light'} btn-sm">
                                                    <i class="fas fa-heart"></i>
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>`;
                        container.innerHTML += card;
                    });
                });
        }

        function loadAllGames(filter = 'all') {
            // Try to load from backend first
            fetch(`${API_BASE_URL}/games`)
                .then(response => response.json())
                .then(data => {
                    let gamesToDisplay = filter === 'all' 
                        ? data 
                        : data.filter(game => game.category === filter);

                    const container = document.getElementById('all-games-container');
                    if (!container) return;
                    
                    container.innerHTML = '';

                    gamesToDisplay.forEach(game => {
                        const card = `
                            <div class="col-md-6 col-lg-4 mb-4">
                                <div class="game-detail-card">
                                    <div class="row">
                                        <div class="col-md-4">
                                            <img src="${game.imageUrl}" alt="${game.title}" class="game-detail-img" onerror="this.src='https://placehold.co/300x400/1a1a2e/fff?text=${encodeURIComponent(game.title)}'">
                                        </div>
                                        <div class="col-md-8">
                                            <h5 class="game-title">${game.title}</h5>
                                            <div class="game-meta">
                                                <span><i class="fas fa-calendar"></i> ${game.releaseDate}</span> | 
                                                <span><i class="fas fa-user"></i> ${game.developer}</span> | 
                                                <span><i class="fas fa-star"></i> ${game.rating}</span>
                                            </div>
                                            <p class="game-description mb-2">${game.description}</p>
                                            <div class="mb-2">
                                                ${(game.tags || []).map(tag => `<span class="badge bg-secondary me-1">${tag}</span>`).join('')}
                                            </div>
                                            <div class="d-flex justify-content-between align-items-center mt-3">
                                                <div>
                                                    <span class="game-price">${getCurrencySymbol()}${getPriceInCurrentCurrency(game.priceUSD)}</span>
                                                    <span class="price-badge ms-2">~$${game.priceUSD}</span>
                                                </div>
                                                <div>
                                                    <button onclick="addToCart(${game.id})" class="btn btn-game btn-sm me-2">Add to Cart</button>
                                                    <button onclick="toggleWishlist(${game.id})" class="btn ${isInWishlist(game.id) ? 'btn-outline-danger' : 'btn-outline-light'} btn-sm">
                                                        <i class="fas fa-heart"></i>
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>`;
                        container.innerHTML += card;
                    });
                })
                .catch(err => {
                    // Fallback to localStorage
                    const games = JSON.parse(localStorage.getItem('games')) || gameDatabase;
                    let gamesToDisplay = filter === 'all' 
                        ? games 
                        : games.filter(game => game.category === filter);

                    const container = document.getElementById('all-games-container');
                    if (!container) return;
                    
                    container.innerHTML = '';

                    gamesToDisplay.forEach(game => {
                        const card = `
                            <div class="col-md-6 col-lg-4 mb-4">
                                <div class="game-detail-card">
                                    <div class="row">
                                        <div class="col-md-4">
                                            <img src="${game.imageUrl}" alt="${game.title}" class="game-detail-img" onerror="this.src='https://placehold.co/300x400/1a1a2e/fff?text=${encodeURIComponent(game.title)}'">
                                        </div>
                                        <div class="col-md-8">
                                            <h5 class="game-title">${game.title}</h5>
                                            <div class="game-meta">
                                                <span><i class="fas fa-calendar"></i> ${game.releaseDate}</span> | 
                                                <span><i class="fas fa-user"></i> ${game.developer}</span> | 
                                                <span><i class="fas fa-star"></i> ${game.rating}</span>
                                            </div>
                                            <p class="game-description mb-2">${game.description}</p>
                                            <div class="mb-2">
                                                ${game.tags.map(tag => `<span class="badge bg-secondary me-1">${tag}</span>`).join('')}
                                            </div>
                                            <div class="d-flex justify-content-between align-items-center mt-3">
                                                <div>
                                                    <span class="game-price">${getCurrencySymbol()}${getPriceInCurrentCurrency(game.priceUSD)}</span>
                                                    <span class="price-badge ms-2">~$${game.priceUSD}</span>
                                                </div>
                                                <div>
                                                    <button onclick="addToCart(${game.id})" class="btn btn-game btn-sm me-2">Add to Cart</button>
                                                    <button onclick="toggleWishlist(${game.id})" class="btn ${isInWishlist(game.id) ? 'btn-outline-danger' : 'btn-outline-light'} btn-sm">
                                                        <i class="fas fa-heart"></i>
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>`;
                        container.innerHTML += card;
                    });
                });
        }

        function filterGames(category) {
            // Update active filter button
            document.querySelectorAll('.filter-btn').forEach(btn => btn.classList.remove('active'));
            event.target.classList.add('active');
            
            loadAllGames(category === 'all' ? 'all' : category);
        }

        function searchGames() {
            const searchTerm = document.getElementById('gameSearch').value.toLowerCase();
            const games = JSON.parse(localStorage.getItem('games')) || gameDatabase;
            
            if (!searchTerm) {
                loadAllGames();
                return;
            }

            const filteredGames = games.filter(game => 
                game.title.toLowerCase().includes(searchTerm) || 
                game.description.toLowerCase().includes(searchTerm) ||
                game.tags.some(tag => tag.toLowerCase().includes(searchTerm))
            );

            const container = document.getElementById('all-games-container');
            container.innerHTML = '';

            if (filteredGames.length === 0) {
                container.innerHTML = `
                    <div class="col-12 text-center py-5">
                        <h4 class="text-white">No games found</h4>
                        <p class="text-muted">Try searching for something else</p>
                    </div>`;
                return;
            }

            filteredGames.forEach(game => {
                const card = `
                    <div class="col-md-6 col-lg-4 mb-4">
                        <div class="game-detail-card">
                            <div class="row">
                                <div class="col-md-4">
                                    <img src="${game.imageUrl}" alt="${game.title}" class="game-detail-img" onerror="this.src='https://placehold.co/300x400/1a1a2e/fff?text=${encodeURIComponent(game.title)}'">
                                </div>
                                <div class="col-md-8">
                                    <h5 class="game-title">${game.title}</h5>
                                    <div class="game-meta">
                                        <span><i class="fas fa-calendar"></i> ${game.releaseDate}</span> | 
                                        <span><i class="fas fa-user"></i> ${game.developer}</span> | 
                                        <span><i class="fas fa-star"></i> ${game.rating}</span>
                                    </div>
                                    <p class="game-description mb-2">${game.description.substring(0, 150)}...</p>
                                    <div class="d-flex justify-content-between align-items-center mt-3">
                                        <span class="game-price">${getCurrencySymbol()}${getPriceInCurrentCurrency(game.priceUSD)}</span>
                                        <button onclick="addToCart(${game.id})" class="btn btn-game btn-sm">Add to Cart</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>`;
                container.innerHTML += card;
            });
        }

        function updateCategoryCounts() {
            const games = JSON.parse(localStorage.getItem('games')) || gameDatabase;
            const categories = ['FPS', 'RPG', 'Action-Adventure', 'MMO', 'Arcade', 'Racing', 'Sports', 'Strategy', 'Horror'];
            
            categories.forEach(category => {
                const count = games.filter(game => game.category === category).length;
                const element = document.getElementById(`count-${category.toLowerCase().replace(' ', '-').replace('-adventure', '-action')}`);
                if (element) {
                    element.textContent = `${count} games`;
                }
            });
        }

        // ===================== HELPER FUNCTIONS =====================
        function generateGameKey(gameTitle) {
            const prefix = gameTitle.substring(0, 4).toUpperCase().replace(/\s/g, '');
            const random = Math.random().toString(36).substr(2, 9).toUpperCase();
            return `${prefix}-${random.substring(0, 4)}-${random.substring(4, 8)}-${random.substring(8, 12)}`;
        }

        function copyToClipboard(text) {
            navigator.clipboard.writeText(text).then(() => {
                alert('Copied to clipboard!');
            });
        }

        function editProfile() {
            const newName = prompt("Enter new name:", currentUser.name);
            if (newName && newName.trim() !== '') {
                currentUser.name = newName.trim();
                saveUser(currentUser);
                loadProfile();
                alert("Profile updated successfully!");
            }
        }

        // ===================== EVENT LISTENERS =====================
        document.addEventListener("DOMContentLoaded", () => {
            // Initialize storage
            initializeStorage();
            
            // Get current user
            currentUser = getCurrentUser();
            
            // Get settings
            const settings = JSON.parse(localStorage.getItem('settings'));
            
            // Set currency and language from settings or localStorage
            currentCurrency = localStorage.getItem('currentCurrency') || settings.defaultCurrency || 'USD';
            currentLanguage = localStorage.getItem('currentLanguage') || settings.defaultLanguage || 'en';
            conversionRate = settings.currencyRate || 3.75;
            
            // Apply language
            changeLanguage(currentLanguage);
            
            // Update UI for user
            updateUIForUser();
            
            // Load home page
            loadGames('all');
            updateCategoryCounts();
            
            // Setup event listeners
            document.getElementById('loginForm')?.addEventListener('submit', function(e) {
                e.preventDefault();
                const email = document.getElementById('login-email').value;
                const password = document.getElementById('login-password').value;
                
                const result = loginUser(email, password);
                if (result.success) {
                    alert("Login successful!");
                    showPage('home');
                } else {
                    alert(result.message);
                }
            });
            
            document.getElementById('registerForm')?.addEventListener('submit', function(e) {
                e.preventDefault();
                const name = document.getElementById('register-name').value;
                const email = document.getElementById('register-email').value;
                const password = document.getElementById('register-password').value;
                const confirmPassword = document.getElementById('register-confirm').value;
                
                if (password !== confirmPassword) {
                    alert("Passwords do not match!");
                    return;
                }
                
                if (password.length < 6) {
                    alert("Password must be at least 6 characters!");
                    return;
                }
                
                const result = registerUser(name, email, password);
                if (result.success) {
                    alert("Registration successful! You are now logged in.");
                    showPage('home');
                } else {
                    alert(result.message);
                }
            });
            
            document.getElementById('payment-form')?.addEventListener('submit', function(e) {
                e.preventDefault();
                completePayment();
            });
            
            // Add real-time card preview
            const cardInputs = ['card-number', 'expiry-date', 'card-name'];
            cardInputs.forEach(id => {
                const input = document.getElementById(id);
                if (input) {
                    input.addEventListener('input', updateCardPreview);
                    
                    // Format card number with spaces
                    if (id === 'card-number') {
                        input.addEventListener('input', function(e) {
                            let value = e.target.value.replace(/\s/g, '').replace(/\D/g, '');
                            if (value.length > 16) value = value.substring(0, 16);
                            e.target.value = value.replace(/(\d{4})/g, '$1 ').trim();
                        });
                    }
                    
                    // Format expiry date
                    if (id === 'expiry-date') {
                        input.addEventListener('input', function(e) {
                            let value = e.target.value.replace(/\D/g, '');
                            if (value.length >= 2) {
                                value = value.substring(0, 2) + '/' + value.substring(2, 4);
                            }
                            e.target.value = value;
                        });
                    }
                }
            });
            
            // Initialize tooltips
            const tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
            tooltipTriggerList.map(function (tooltipTriggerEl) {
                return new bootstrap.Tooltip(tooltipTriggerEl);
            });
            
            console.log("GameHub Fully Loaded!");
        });
  