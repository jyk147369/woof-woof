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
        <li>
          <i class="fa-solid fa-magnifying-glass"></i>
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
import { computed } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();
const logout = () => {
  sessionStorage.removeItem("atoken");
  router.push("");
  router.go();
}

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
  display: flex; /* flex를 사용하여 WOOF 텍스트와 이미지를 한 줄로 정렬 */
  align-items: center;
  gap: 8px; /* 텍스트와 이미지 사이의 간격을 조절 */
}

.logo .logo-text {
  font-size: 4rem;
  color: black;
  font-weight: 500;
  line-height: 1; /* 텍스트의 위아래 간격을 일정하게 조절 */
}

.logo img {
  width: 80px; /* 이미지의 너비를 80px로 설정 */
  height: 80px; /* 이미지의 높이를 80px로 설정 */
  overflow: hidden;
}

.top-menu {
  display: flex;
  align-items: center;
  gap: 20px;
  position: absolute;
  right: 20px;
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
}
</style>
