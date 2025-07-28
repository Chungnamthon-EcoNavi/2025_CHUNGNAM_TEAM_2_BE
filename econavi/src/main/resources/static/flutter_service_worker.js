'use strict';
const MANIFEST = 'flutter-app-manifest';
const TEMP = 'flutter-temp-cache';
const CACHE_NAME = 'flutter-app-cache';

const RESOURCES = {"assets/AssetManifest.bin": "0bebec17a416e7500f198327b1b76cf2",
"assets/AssetManifest.bin.json": "a58a04887bf2950605caa07510735530",
"assets/AssetManifest.json": "57450e2b57b4ca5ffab1051dfe08b0cb",
"assets/config/membershp_table.json": "f8a8574509659b64d380664943bed000",
"assets/config/page_routing_table.json": "c785e578e26d8a8147bce52729434c63",
"assets/FontManifest.json": "a2f117a32b9a758a15c4fcfac285abe8",
"assets/fonts/MaterialIcons-Regular.otf": "c0ad29d56cfe3890223c02da3c6e0448",
"assets/fonts/Pretendard/Pretendard-Black.woff": "ffac9e667a7d8415953e5982a9ab1d51",
"assets/fonts/Pretendard/Pretendard-Bold.woff": "bd94b933c6839371baa27f7950ef3784",
"assets/fonts/Pretendard/Pretendard-ExtraBold.woff": "b065213da09db107d456c842bcff59ad",
"assets/fonts/Pretendard/Pretendard-ExtraLight.woff": "a8765fcee2563360f3f8117835300c3d",
"assets/fonts/Pretendard/Pretendard-Light.woff": "0fcba49d32bb9e4b3738d28bedb1bdd2",
"assets/fonts/Pretendard/Pretendard-Medium.woff": "4750a6d12c26201887eee28ae55ed037",
"assets/fonts/Pretendard/Pretendard-Regular.woff": "f897fa3ff216e4be74648184144780b1",
"assets/fonts/Pretendard/Pretendard-SemiBold.woff": "e02072832a9d8ef22f3d1d08bb917f9d",
"assets/fonts/Pretendard/Pretendard-Thin.woff": "bf79f0289a1950ddb6cbca0c709b77df",
"assets/NOTICES": "41eab8b3409314a83c545d22279751fb",
"assets/png/filter.png": "49e020b8d0513d4261e7b620bb8c602c",
"assets/png/microphone.png": "ce30b499bd6fcc4881d3a16301c44523",
"assets/png/notice.png": "bcd233c1224692ffc7731ff0e9843dc8",
"assets/shaders/ink_sparkle.frag": "ecc85a2e95f5e9f53123dcaf8cb9b6ce",
"assets/svg/bag.svg": "e0b361f086ec87b8e205aa7bab9d289d",
"assets/svg/bell.svg": "78ea841ea971a5fe061208cb39950661",
"assets/svg/chevron-down.svg": "b4fe7f4e7be9cc4e5d170383385b3143",
"assets/svg/chevron-left.svg": "670dcd7185af126ebb8dc82d71cb06be",
"assets/svg/chevron-right.svg": "91487c48b656a1efe138df3bc84b8d31",
"assets/svg/chevron-up.svg": "322efcfbdcd678406bbfb8162635f225",
"assets/svg/currency-coin-rubel.svg": "3bf7e81a9836e057734ee576cd840f6e",
"assets/svg/dot-vertical.svg": "20f7cad8baebeb17be603d9a0d776b56",
"assets/svg/eco_navi_logo.svg": "6909bdf9b47192321b065ef3789fd3a4",
"assets/svg/eco_navi_text_logo.svg": "b8dcb147f294da6f673ae3f0c34ec8bd",
"assets/svg/favourite.svg": "d9301f2f2a6034880d7aecd6f6714ceb",
"assets/svg/frame.svg": "d25ea1918df0d57dbcfba798063b7a00",
"assets/svg/heart.svg": "5b91fef048639df3d6d9bf54e63a67a3",
"assets/svg/home.svg": "323fbfe562a0706704e2553241780f86",
"assets/svg/image-question.svg": "16fe0c370eff399ae81b6e6c41ee06ac",
"assets/svg/map.svg": "df80276a3fd999f953618cb3a85b882b",
"assets/svg/marker.svg": "542e1630264b864fd5734b83ccdb5a51",
"assets/svg/notice.svg": "753464da97157e4466d0fbde3f639d2b",
"assets/svg/profile.svg": "bb299d32278fad7e85351cd95b4365ea",
"assets/svg/star.svg": "1072aec796ffe38bd13fdb2ca2a6cdd5",
"assets/svg/target.svg": "0542b668d41feebc79476163a256d502",
"assets/svg/ticket.svg": "33353ed606b8f6faec1be9bc3d7df21b",
"assets/svg/tmp_box.svg": "01efeb1a54ff0ec2073b5b544b810e55",
"assets/svg/trip_origin.svg": "493b4596025f460218eaa96fa0d7d1a6",
"canvaskit/canvaskit.js": "728b2d477d9b8c14593d4f9b82b484f3",
"canvaskit/canvaskit.js.symbols": "27361387bc24144b46a745f1afe92b50",
"canvaskit/canvaskit.wasm": "a37f2b0af4995714de856e21e882325c",
"canvaskit/chromium/canvaskit.js": "8191e843020c832c9cf8852a4b909d4c",
"canvaskit/chromium/canvaskit.js.symbols": "f7c5e5502d577306fb6d530b1864ff86",
"canvaskit/chromium/canvaskit.wasm": "c054c2c892172308ca5a0bd1d7a7754b",
"canvaskit/skwasm.js": "ea559890a088fe28b4ddf70e17e60052",
"canvaskit/skwasm.js.symbols": "9fe690d47b904d72c7d020bd303adf16",
"canvaskit/skwasm.wasm": "1c93738510f202d9ff44d36a4760126b",
"favicon.png": "5dcef449791fa27946b3d35ad8803796",
"flutter.js": "83d881c1dbb6d6bcd6b42e274605b69c",
"flutter_bootstrap.js": "6ed2af9540b5706e613b6f054c75242d",
"icons/Icon-192.png": "ac9a721a12bbc803b44f645561ecb1e1",
"icons/Icon-512.png": "96e752610906ba2a93c65f8abe1645f1",
"icons/Icon-maskable-192.png": "c457ef57daa1d16f64b27b786ec2ea3c",
"icons/Icon-maskable-512.png": "301a7604d45b3e739efc881eb04896ea",
"index.html": "ea495857fae20c9a643d2f556dce793a",
"/": "ea495857fae20c9a643d2f556dce793a",
"main.dart.js": "7c459a6bed9f7a964ed29c3ff4ad6dd7",
"manifest.json": "a363549daa2905bb07a4ca607df60955",
"version.json": "388751e5ba3abf29364361dc74cc29af"};
// The application shell files that are downloaded before a service worker can
// start.
const CORE = ["main.dart.js",
"index.html",
"flutter_bootstrap.js",
"assets/AssetManifest.bin.json",
"assets/FontManifest.json"];

// During install, the TEMP cache is populated with the application shell files.
self.addEventListener("install", (event) => {
  self.skipWaiting();
  return event.waitUntil(
    caches.open(TEMP).then((cache) => {
      return cache.addAll(
        CORE.map((value) => new Request(value, {'cache': 'reload'})));
    })
  );
});
// During activate, the cache is populated with the temp files downloaded in
// install. If this service worker is upgrading from one with a saved
// MANIFEST, then use this to retain unchanged resource files.
self.addEventListener("activate", function(event) {
  return event.waitUntil(async function() {
    try {
      var contentCache = await caches.open(CACHE_NAME);
      var tempCache = await caches.open(TEMP);
      var manifestCache = await caches.open(MANIFEST);
      var manifest = await manifestCache.match('manifest');
      // When there is no prior manifest, clear the entire cache.
      if (!manifest) {
        await caches.delete(CACHE_NAME);
        contentCache = await caches.open(CACHE_NAME);
        for (var request of await tempCache.keys()) {
          var response = await tempCache.match(request);
          await contentCache.put(request, response);
        }
        await caches.delete(TEMP);
        // Save the manifest to make future upgrades efficient.
        await manifestCache.put('manifest', new Response(JSON.stringify(RESOURCES)));
        // Claim client to enable caching on first launch
        self.clients.claim();
        return;
      }
      var oldManifest = await manifest.json();
      var origin = self.location.origin;
      for (var request of await contentCache.keys()) {
        var key = request.url.substring(origin.length + 1);
        if (key == "") {
          key = "/";
        }
        // If a resource from the old manifest is not in the new cache, or if
        // the MD5 sum has changed, delete it. Otherwise the resource is left
        // in the cache and can be reused by the new service worker.
        if (!RESOURCES[key] || RESOURCES[key] != oldManifest[key]) {
          await contentCache.delete(request);
        }
      }
      // Populate the cache with the app shell TEMP files, potentially overwriting
      // cache files preserved above.
      for (var request of await tempCache.keys()) {
        var response = await tempCache.match(request);
        await contentCache.put(request, response);
      }
      await caches.delete(TEMP);
      // Save the manifest to make future upgrades efficient.
      await manifestCache.put('manifest', new Response(JSON.stringify(RESOURCES)));
      // Claim client to enable caching on first launch
      self.clients.claim();
      return;
    } catch (err) {
      // On an unhandled exception the state of the cache cannot be guaranteed.
      console.error('Failed to upgrade service worker: ' + err);
      await caches.delete(CACHE_NAME);
      await caches.delete(TEMP);
      await caches.delete(MANIFEST);
    }
  }());
});
// The fetch handler redirects requests for RESOURCE files to the service
// worker cache.
self.addEventListener("fetch", (event) => {
  if (event.request.method !== 'GET') {
    return;
  }
  var origin = self.location.origin;
  var key = event.request.url.substring(origin.length + 1);
  // Redirect URLs to the index.html
  if (key.indexOf('?v=') != -1) {
    key = key.split('?v=')[0];
  }
  if (event.request.url == origin || event.request.url.startsWith(origin + '/#') || key == '') {
    key = '/';
  }
  // If the URL is not the RESOURCE list then return to signal that the
  // browser should take over.
  if (!RESOURCES[key]) {
    return;
  }
  // If the URL is the index.html, perform an online-first request.
  if (key == '/') {
    return onlineFirst(event);
  }
  event.respondWith(caches.open(CACHE_NAME)
    .then((cache) =>  {
      return cache.match(event.request).then((response) => {
        // Either respond with the cached resource, or perform a fetch and
        // lazily populate the cache only if the resource was successfully fetched.
        return response || fetch(event.request).then((response) => {
          if (response && Boolean(response.ok)) {
            cache.put(event.request, response.clone());
          }
          return response;
        });
      })
    })
  );
});
self.addEventListener('message', (event) => {
  // SkipWaiting can be used to immediately activate a waiting service worker.
  // This will also require a page refresh triggered by the main worker.
  if (event.data === 'skipWaiting') {
    self.skipWaiting();
    return;
  }
  if (event.data === 'downloadOffline') {
    downloadOffline();
    return;
  }
});
// Download offline will check the RESOURCES for all files not in the cache
// and populate them.
async function downloadOffline() {
  var resources = [];
  var contentCache = await caches.open(CACHE_NAME);
  var currentContent = {};
  for (var request of await contentCache.keys()) {
    var key = request.url.substring(origin.length + 1);
    if (key == "") {
      key = "/";
    }
    currentContent[key] = true;
  }
  for (var resourceKey of Object.keys(RESOURCES)) {
    if (!currentContent[resourceKey]) {
      resources.push(resourceKey);
    }
  }
  return contentCache.addAll(resources);
}
// Attempt to download the resource online before falling back to
// the offline cache.
function onlineFirst(event) {
  return event.respondWith(
    fetch(event.request).then((response) => {
      return caches.open(CACHE_NAME).then((cache) => {
        cache.put(event.request, response.clone());
        return response;
      });
    }).catch((error) => {
      return caches.open(CACHE_NAME).then((cache) => {
        return cache.match(event.request).then((response) => {
          if (response != null) {
            return response;
          }
          throw error;
        });
      });
    })
  );
}
