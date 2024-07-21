<template>
  <div id="myPage">
    <h2 class="mypagetxt">마이페이지</h2>
    <div class="profile">
      <img
          src="../../public/static/images/salon.jpg"
          alt="Profile Image"
          class="profile-image"
      />
      <p class="username">{{ memberList?.nickname }}</p>
      <p class="email">{{ memberList?.email }}</p>
    </div>
    <div id="navigation">
      <button v-on:click="getData()">예약 내역</button>
      <button @click="setActiveSection('updateProfile')">회원 정보 수정</button>
    </div>
  </div>

  <div v-show="activeSection === 'updateProfile'" id="updateProfileSection">
    <div>
      <form @submit.prevent="submitForm">
        <label for="email">이메일:</label>
        <input
            v-model="formData.email"
            type="text"
            id="email"
            name="email"
            required
        />

        <label for="password">새 비밀번호:</label>
        <input
            v-model="formData.password"
            type="password"
            id="password"
            name="password"
            required
        />

        <label for="nickname">새 닉네임:</label>
        <input
            v-model="formData.nickname"
            type="text"
            id="nickname"
            name="nickname"
            required
        />

        <button type="submit" class="button">수정</button>
      </form>
    </div>
  </div>

  <div v-show="activeSection === 'reservation'" id="reservationSection">
    <h3>예약 내역</h3>
    <ul id="reservationList">
      <!-- 예약 내역이 동적으로 추가될 부분 -->
    </ul>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      activeSection: "",
      memberList: {},
      formData: {
        email: "",
        password: "",
        nickname: ""
      },
    };
  },
  methods: {
    setActiveSection(section) {
      this.activeSection = section;
    },
    async getData() {
      const memberIdx = sessionStorage.getItem("memberIdx");
      if (memberIdx) {
        this.$router.push("/orders/mylist?idx=" + memberIdx);
      } else {
        alert("로그인 정보가 없습니다.");
      }
    },
    async submitForm() {
      let data = {
        email: this.formData.email,
        password: this.formData.password,
        nickname: this.formData.nickname
      };

      let memberUpdateReq = JSON.stringify(data);

      console.log(memberUpdateReq);

      try {
        let response = await axios.patch(
            "http://localhost:8080/member/update",
            memberUpdateReq,
            {
              headers: {
                "Content-Type": "application/json",
              },
            }
        );

        console.log(response.data);

        alert("회원 정보가 성공적으로 수정되었습니다.");
      } catch (error) {
        console.error(error);
        alert("회원 정보 수정에 실패했습니다.");
      }
    },
    async mounted() {
      await this.fetchUserData();
    },
    async fetchUserData() {
      const token = window.sessionStorage.getItem('atoken');

      if (!token) {
        console.log('No token found');
        return;
      }

      const decodedToken = atob(token.split('.')[1]);
      const email = JSON.parse(decodedToken).username;

      try {
        const response = await axios.get(`http://localhost:8080/member/${email}`);
        console.log(response.data);
        this.memberList = response.data;
      } catch (error) {
        console.error(error);
      }
    }
  },
  async mounted() {
    await this.fetchUserData();
  },
};
</script>

<style scoped>
body {
  font-family: 'Arial', sans-serif;
  background-color: #f8f8f8;
  margin: 0;
  padding: 0;
}

#myPage {
  max-width: 600px;
  margin: 20px auto;
  padding: 20px;
  background: #ffffff;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.mypagetxt {
  font-size: 32px;
  font-weight: bold;
  text-align: center;
  color: #333;
  margin-bottom: 20px;
}

.profile {
  text-align: center;
  margin-bottom: 30px;
}

.profile-image {
  width: 120px;
  height: 120px;
  border-radius: 50%;
  object-fit: cover;
  margin-bottom: 10px;
  border: 2px solid #faef71;
}

.username {
  font-size: 24px;
  font-weight: bold;
  color: #333;
  margin-bottom: 5px;
}

.email {
  font-size: 16px;
  color: #666;
}

#navigation {
  display: flex;
  justify-content: space-around;
  margin-bottom: 20px;
}

#navigation button {
  background-color: #ffd60a;
  color: #000;
  border: none;
  padding: 10px 20px;
  font-size: 16px;
  cursor: pointer;
  border-radius: 5px;
  transition: background-color 0.3s ease;
}

#navigation button:hover {
  background-color: #f2e256 ;
}

#updateProfileSection,
#reservationSection {
  max-width: 600px;
  margin: 0 auto;
  background: #fff;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

h3 {
  text-align: center;
  color: #333;
  margin-bottom: 20px;
}

form {
  display: flex;
  flex-direction: column;
}

label {
  margin-bottom: 5px;
  font-size: 14px;
  color: #333;
}

input {
  padding: 10px;
  margin-bottom: 15px;
  border: 1px solid #ddd;
  border-radius: 5px;
  font-size: 14px;
  box-sizing: border-box;
}

button[type="submit"] {
  background-color: #ffd60a;
  color: #000;
  border: none;
  padding: 10px;
  font-size: 16px;
  cursor: pointer;
  border-radius: 5px;
  transition: background-color 0.3s ease;
}

button[type="submit"]:hover {
  background-color: #f2e256;
}
</style>
