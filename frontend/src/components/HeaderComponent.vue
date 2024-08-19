<template>
  <header>
    <div class="top-bar">
      <div class="logo-container">
        <h1 class="logo">
          <a href="/">
            <span class="logo-text">WOOF</span>
            <img class="woofpic" src="https://github.com/user-attachments/assets/0e52bd0c-a21f-4365-9c5d-d28012b633b2" alt="Woof Logo" />
          </a>
        </h1>
      </div>
      <ul class="top-menu">
        <li class="search-container">
          <input
              v-model="searchQuery"
              @keyup.enter="performSearch"
              type="text"
              placeholder="Search..."
              class="search-box"
          />
          <i class="fa-solid fa-magnifying-glass" @click="performSearch"></i>
        </li>
        <li v-show="!isLoggedIn">
          <a href="/signup/member">SignUp</a>
        </li>
        <li v-show="!isLoggedIn">
          <a href="/login/member">Login</a>
        </li>
        <li v-show="isLoggedIn">
          <a @click="logout()">로그아웃</a>
        </li>
        <li>
          <a href="/mypage">My Page</a>
        </li>
      </ul>
    </div>
    <div class="bottom-bar">
      <ul class="gnb">
        <li>
          <a href="/productCeo/list">가게</a>
        </li>
        <li>
          <a href="/productManager/list">매니저</a>
        </li>
        <li>
          <a href="/orders/create">예약</a>
        </li>
      </ul>
    </div>
  </header>

</template>

<script setup>
import { ref, computed } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();
const searchQuery = ref('');

const performSearch = () => {
  if (searchQuery.value.trim() !== '') {
    router.push({ path: '/search', query: { q: searchQuery.value } });
  }
};

const logout = () => {
  sessionStorage.removeItem("atoken");
  router.push("");
  router.go();
};

const isLoggedIn = computed(() => {
  return sessionStorage.getItem("atoken") !== null;
});
</script>

<style>
body {
  margin: 0;
  font-family: Arial, sans-serif;
}

header {
  width: 100%;
  height: 200px;
}

.top-bar {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 10px 20px;
  background-color: white;
  position: relative;
  height: 50%;
  overflow: hidden;
}

.logo-container {
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
  display: flex;
  align-items: center;
}

.logo {
  display: flex;
  align-items: center;
  gap: 8px;
}

.logo .logo-text {
  font-size: 4rem;
  color: black;
  font-weight: 500;
  line-height: 1;
}

.logo img {
  width: 80px;
  height: 80px;
  overflow: hidden;
}

.top-menu {
  display: flex;
  align-items: center;
  gap: 20px;
  position: absolute;
  right: 50px;
}

.top-menu li {
  list-style: none;
}

.top-menu a {
  text-decoration: none;
  color: black;
  font-weight: 400;
}

.bottom-bar {
  background-color: white;
  border-top: 1px solid #ddd;
  padding: 10px 0;
  display: flex;
  justify-content: center;
  height: 60px;
  align-items: center;
  border-bottom: 1px solid #00000017;
}

.gnb {
  display: flex;
  gap: 100px;
}

.gnb li {
  list-style: none;
}

.gnb a {
  text-decoration: none;
  color: black;
  font-size: 20px;
  font-weight: 400;
}

.gnb li:hover a {
  color: #d2ba32;
}

.fa-magnifying-glass {
  font-size: 18px;
  cursor: pointer;
}

.search-container {
  display: flex;
  align-items: center;
}

.search-box {
  width: 150px;
  padding: 5px;
  border: 1px solid #ddd;
  border-radius: 5px;
  outline: none;
  margin-right: 10px;
  transition: all 0.3s ease;
}
</style>
