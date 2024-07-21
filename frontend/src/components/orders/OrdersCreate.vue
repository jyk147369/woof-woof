<template>
  <div class="all">
    <div class="select">
      <div class="selectCeo">
        <router-link to="/productCeo/list" class="ceoButton">가게 선택하기</router-link>
        <p class="ceo"> {{ storeName }}</p>
      </div>
      <div class="selectManager">
        <router-link to="/productManager/list" class="ceoButton">매니저 선택하기</router-link>
        <p class="manager">{{ managerName }}</p>
      </div>
    </div>
    <form class="orderform" @submit.prevent="sendData">
      <div>
        <label for="name">이름</label>
        <input type="text" id="name" maxlength="30" placeholder="ex.홍길동/포롱" v-model="name" />
      </div>
      <div>
        <label for="phoneNumber">전화번호</label>
        <input type="tel" id="phoneNumber" maxlength="11" placeholder="ex.01011112222" v-model="phoneNumber" />
      </div>
      <div>
        <label for="time">예약시간</label>
        <input type="tel" id="time" maxlength="11" placeholder="숫자만 써주세요" v-model="time" />
      </div>
      <div>
        <label for="orderDetails">특이사항</label>
        <input type="text" id="orderDetails" maxlength="50" placeholder="ex. 입질이 있어요" v-model="orderDetails" />
      </div>
      <div class="service">
        <label for="place">장소</label>
        <input type="text" id="place" maxlength="50" placeholder="장소" v-model="place" />
      </div>
<!--      <div class="numbers">-->
<!--        <p>업체 번호: {{ productCeoIdx }} </p>&nbsp;-->
<!--        <p>매니저 번호: {{ productManagerIdx }} </p>&nbsp;-->
<!--        <p>나의 번호: {{ memberIdx }} </p>-->
<!--      </div>-->
      <input class="submit signup-progress-btn" type="submit" value="전송하기" />
    </form>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      name: "",
      phoneNumber: "",
      time: "",
      orderDetails: "",
      place: "",
      // storeName: localStorage.getItem("storeName") || "업체를 선택해주세요",
      productCeoIdx: localStorage.getItem("productCeoIdx"),
      // managerName: localStorage.getItem("managerName") || "매니저를 선택해주세요",
      productManagerIdx: localStorage.getItem("productManagerIdx"),
      memberIdx: localStorage.getItem('memberIdx') || "로그인한 사용자의 idx가 없습니다"
    };
  },
  methods: {
    async sendData() {
      let data = {
        name: this.name,
        phoneNumber: this.phoneNumber,
        time: this.time,
        orderDetails: this.orderDetails,
        place: this.place,
        storeName: this.storeName,
        productCeoIdx: this.productCeoIdx,
        productManagerIdx: this.productManagerIdx,
        memberIdx: this.memberIdx,
      };

      try {
        await axios.post("http://localhost:8080/api/orders/create", data, {
          headers: { "Content-Type": "application/json" },
        });
        alert("주문이 완료되었습니다.");
        this.$router.push("/orders/success");
      } catch (error) {
        console.error(error);
        alert("주문을 실패하였습니다.");
      }
    },
  },
};
</script>

<style scoped>
.all {
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 73px;
}

.select {
  width: 400px;
  display: flex;
  /* flex-direction: column; */
  /* align-items: flex-start; */
  /* margin: 0 auto; */
  /* margin-top: 20px; */
  justify-content: space-between;
  //background: red;
}

.selectCeo, .selectManager {
  margin-bottom: 20px;
}

.ceoButton {
  font-size: 18px;
  /* background-color: #000000; */
  padding: 10px 20px;
  border-radius: 10px;
  /* color: white; */
  border: 1px solid black;
  transition: background-color 0.3s ease;
}

.ceoButton:hover {
  background-color: #ffd60a;
}

.ceo, .manager {
  font-size: 16px;
  margin-top: 10px;
}

.numbers {
  display: flex;
  justify-content: center;
  font-size: 14px;
  margin-top: 20px;
}

.orderform {
  width: 100%;
  max-width: 600px;
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 20px 0;
  padding: 20px;
  background: #fff;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.orderform > div {
  width: 100%;
  margin-bottom: 15px;
}

.orderform > div > label {
  display: block;
  margin-bottom: 5px;
  font-size: 14px;
  color: #555;
}

.orderform > div > input {
  width: 95%;
  height: 40px;
  padding: 5px 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
  font-size: 14px;
  background: #f9f9f9;
}

.service {
  width: 100%;
}

#orderDetails {
  height: 80px;
}

.submit {
  width: 200px;
  height: 50px;
  margin-top: 20px;
  border: none;
  background-color: #ffd60a;
  border-radius: 10px;
  font-size: 16px;
  font-weight: bold;
  color: #333;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.submit:hover {
  background-color: #fff2b0;
}
</style>
