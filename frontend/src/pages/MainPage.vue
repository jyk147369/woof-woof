<template>
  <ul class="nav">
    <li class="nav-item nav-item-best"></li>
  </ul>
  <div id="home-container">
    <div class="desktop hidden-s">
      <section class="introduction-section">
        <img class="cat" src="https://github.com/user-attachments/assets/29ba684e-2326-4670-b083-e23689b5475d">
        <div class="circle"></div>
        <div class="circle2"></div>
        <img class="dog" src="https://github.com/user-attachments/assets/e1234488-66ea-49ce-8a64-9078f3e17a5a">
        <img class="paw" src="https://github.com/user-attachments/assets/7f74e859-b601-4ef9-a0a4-61036390f3cd" alt="Paw Print" />
        <div class="circle-pink"></div>
        <div class="circle-yellow"></div>
        <div class="box1 hidden-content">
          <div class="container">
            <p class="header-text">우리아이<br />FRIENDS</p>
            <p class="sub-text">반려가족의 하루에 WOOF가 함께하겠습니다.</p>
          </div>
        </div>
      </section>
    </div>
  </div>
  <div class="box6 hidden-content">
    <div class="box7">
      <div class="text1">
        <h1 class="text2">매니저가<br />집으로 와요!</h1>
        <h3 class="text3">
          원하는 픽업 장소로<br />언제 어디서든 WOOF 매니저를 불러보세요!
        </h3>
        <a href="/productManager/list">
          <div class="box10">
            <p class="p1">자세히 보기</p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <i class="fa-solid fa-arrow-right"></i>
          </div>
        </a>
      </div>
      <img
          src="https://github.com/user-attachments/assets/fe32bf1e-55c2-4119-bee6-d450af2bca1b"
          alt="baby"
          style="width: 500px; height: 450px; border-radius: 2px"
      />
    </div>
    <div class="box11 hidden-content">
      <img
          src="https://github.com/user-attachments/assets/6600253c-385a-4d1b-ba14-a05c4195071c"
          style="width: 570px; height: 450px; border-radius: 2px"
      />
      <div class="text4">
        <h2 class="text5">믿을 수 있는 업체!</h2>
        <h3 class="text6">
          WOOF가 엄선한 업체 믿고 맡겨보세요
        </h3>
        <a href="/productCeo/list"
        ><div class="text7">
          <p class="p2">자세히 보기</p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <i class="fa-solid fa-arrow-right"></i>
        </div
        ></a>
      </div>
    </div>
    <div class="box12 hidden-content">
      <div class="text8">
        <h1 class="text9">그루머의<br />미용 서비스</h1>
        <h3 class="text10">
          우리 동네 애견 미용샵들을 한 눈에 비교하고,<br />
          예약부터 결제까지 간편하게 해결하세요.
        </h3>
        <a href="/orders/create"
        ><div class="box15">
          <p class="p4">자세히 보기</p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <i class="fa-solid fa-arrow-right"></i>
        </div
        ></a>
      </div>
      <img
          src="https://github.com/user-attachments/assets/07b9c749-8279-460d-a3f9-f2da1094c167"
          style="width: 500px; height: 450px; border-radius: 2px"
      />
    </div>
  </div>

  <div class="review2 hidden-content">
    <div class="review3">
      <img class="partner2" src="https://github.com/user-attachments/assets/0c651c6f-3b6e-4516-83a9-2ee2ac26ad2d">
      <div class="review4">
        <h2 class="review5">WOOF 파트너로 함께해요!</h2>
        <h3 class="banner1">
          반려가족에게 행복한 시간을 선물할 수 있도록<br />WOOF와 함께 성장할
          파트너를 찾습니다.
        </h3>
        <a class="banner2" href="/signup/manager">
          <p class="p9">WOOF 매니저 지원</p>
          <i class="fa-solid fa-arrow-right"></i></a
        ><a class="partner1" href="/signup/ceo">
        <p class="p10">WOOF 제휴업체 지원</p>
        <i class="fa-solid fa-arrow-right"></i>
      </a>
      </div>
    </div>
  </div>


</template>

<script>
export default {
  mounted() {
    const revealElements = document.querySelectorAll('.hidden-content');

    const handleScroll = () => {
      revealElements.forEach(element => {
        const rect = element.getBoundingClientRect();
        if (rect.top < window.innerHeight && rect.bottom > 0) {
          element.classList.add('show');
        } else {
          element.classList.remove('show');
        }
      });
    };

    window.addEventListener('scroll', handleScroll);
    window.addEventListener('load', handleScroll);
  }
};
</script>

<style scoped>
.box1 {
  display: flex;
  position: relative;
  width: 100%;
  height: 673px;
  //background-color: #faef71;
  align-items: center;
  padding-top: 50px;
  justify-content: center;
}

.box2 {
  display: flex;
  justify-content: flex-end;
  align-items: flex-end;
  width: 1440px;
  height: 770px;
  background: #faef71;
  position: relative;
}

.box3 {
  display: flex;
  flex-direction: column;
  align-self: center;
  position: absolute;
  top: 229px;
  left: 0;
  right: 0;
  margin: 0px auto;
  width: 1440px;
  z-index: 20;
}
.box6 {
  width: 1440px;
  margin: 0px auto;
  background-color: white;
  margin-top: 170px;
  padding-bottom: 180px;
  position: relative;
}

.box7 {
  display: flex;
  justify-content: space-between;
  padding: 0px 220px 0px 220px;
}
.box10 {
  width: 170px;
  height: 59px;
  margin-top: 50px;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #ffd60a;
}

.box11 {
  display: flex;
  margin-top: 110px;
  //background-color: #faef71;
}

.container {
  margin-left: 240px;
}

.header-text {
  display: flex;
  flex-direction: column;
  align-self: flex-start;
  font-weight: 600;
  font-size: 85px;
  color: #333333;
}

.sub-text-container {
  margin-top: 30px;
}

.sub-text {
  font-weight: 500;
  margin-top: 38px;
  font-size: 26px;
}

.text1 {
  padding-top: 72px;
}
.text2 {
  font-size: 47px;
  color: #333333;
  font-weight: 600;
  line-height: 67px;
}

.text3 {
  font-weight: 400;
  margin-top: 38px;
  font-size: 17px;
  color: #333333;
  letter-spacing: -0.1px;
  line-height: normal;
}

.p1 {
  font-size: 17px;
  color: #333333;
  font-weight: 600;
  letter-spacing: -0.2px;
}

.text4 {
  margin-left: 125px;
}

.text5 {
  margin: 55px;
  font-size: 47px;
  color: #333333;
  font-weight: 600;
  line-height: 67px;
}
.text6 {
  font-weight: 400;
  margin-top: 38px;
  font-size: 17px;
  color: #333333;
  letter-spacing: -0.1px;
  line-height: normal;
  margin: 55px;
}

.text7 {
  width: 170px;
  height: 59px;
  margin-top: 50px;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-left: 55px;
  //border: 1px solid black;
  background: #ffd60a;
}

.p2 {
  font-size: 17px;
  color: #333333;
  font-weight: 600;
  letter-spacing: -0.2px;
}
.box12 {
  display: flex;
  justify-content: space-between;
  padding: 0px 220px 0px 220px;
  margin-top: 110px;
}

.text8 {
  padding-top: 72px;
}

.text9 {
  font-size: 47px;
  color: #333333;
  font-weight: 600;
  line-height: 67px;
}

.text10 {
  font-weight: 400;
  margin-top: 38px;
  font-size: 17px;
  color: #333333;
  letter-spacing: -0.1px;
  line-height: normal;
}

.box15 {
  width: 170px;
  height: 59px;
  margin-top: 50px;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #ffd60a;
}

.p4 {
  font-size: 17px;
  color: #333333;
  font-weight: 600;
  letter-spacing: -0.2px;
}

.box16 {
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  background: #f7f7f8;
  padding-top: 166px;
  padding-bottom: 180px;
}

.text11 {
  font-weight: 700;
  letter-spacing: 0.2px;
  margin-top: 21px;
  font-size: 50px;
  line-height: 70px;
  color: #555555;
}

.text12 {
  padding-left: 45px;
  display: flex;
  align-items: center;
  margin-top: 34px;
}

.text13 {
  border-bottom: 2px solid #6a92fe;
  margin-right: 24px;
  padding-bottom: 5px;
  cursor: pointer;
}

.p5 {
  color: #6a92fe;
  font-size: 17px;
  font-weight: 600;
}

.text14 {
  border-bottom: 2px solid transparent;
  margin-right: 24px;
  padding-bottom: 5px;
  cursor: pointer;
}

.p6 {
  color: #bcbcbc;
  font-size: 17px;
  font-weight: 600;
}

.box17 {
  display: flex;
  flex-wrap: wrap;
  width: 1000px;
  margin-top: 55px;
  justify-content: space-between;
  align-items: center;
}

.box18 {
  width: 310px;
  height: 230px;
  background: #ffffff;
  border-radius: 10px;
  box-shadow: 0px 2px 12px rgba(0, 0, 0, 0.09);
  margin: 16px 0px 16px 0px;
  padding: 21px;
}

.box19 {
  display: flex;
  position: relative;
  flex-direction: row;
  justify-content: space-between;
}

.box20 {
  position: absolute;
  display: flex;
  top: 30px;
  left: 0;
  right: 0;
  height: 17px;
  justify-content: center;
  align-items: center;
}

.review1 {
  margin-top: 60px;
  width: 600px;  /* 이 부분을 변경해 주세요. */
  height: 59px;
  border: 1px solid #6a92fe;
  border-radius: 30px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.p8 {
  font-weight: 600;
  color: #6a92fe;
  font-size: 18px;
  letter-spacing: -0.2px;
}

.review2 {
  //background-color: #faef71;
  height: 700px;
}

.review3 {
  padding-top: 184px;
  width: 1116px;
  margin: 0px auto;
  height: 100%;
  display: flex;
}

.review4 {
  position: relative;
  height: 100%;
  padding-left: 55px;
}
.review5 {
  font-weight: 600;
  letter-spacing: 0.2px;
  font-size: 46px;
  line-height: 70px;
  color: #333333;
}

.banner1 {
  font-size: 17px;
  font-weight: 400;
  margin-top: 38px;
  color: #333333;
  line-height: 30px;
}

.banner2 {
  width: 250px;
  height: 59px;
  //border: 1.5px solid black;
  margin-top: 59px;
  cursor: pointer;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-left: 29px;
  padding-right: 22px;
  background: #ffd60a;
}

.p9 {
  font-size: 17px;
  color: #333333;
  font-weight: bold;
  letter-spacing: -0.2px;
}

.partner1 {
  width: 250px;
  height: 59px;
  //border: 1.5px solid black;
  margin-top: 24px;
  cursor: pointer;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-left: 29px;
  padding-right: 22px;
  background: #ffd60a;

}

.p10 {
  font-size: 17px;
  color: #333333;
  font-weight: bold;
  letter-spacing: -0.2px;
}

.hidden-content {
  opacity: 0;
  transform: translateY(20px);
  transition: opacity 0.6s ease-out, transform 0.6s ease-out;
}

.show {
  opacity: 1;
  transform: translateY(0);
}

.dog{
  object-fit: contain;
  width: 350px;
  height: 100%;
  position: absolute;
  top: 0;
  right: -19px;
  z-index: 1;
  overflow: hidden;
}
.cat{
  object-fit: contain;
  width: 389px;
  height: 100%;
  position: absolute;
  top: 70px;
  left: -56px;
  transform: rotate(3deg);
  z-index: 1;
  overflow: hidden;

}
.introduction-section{
  position: relative;
}

.circle{
  width: 300px;
  height: 300px;
  border-radius: 50%;
  background: red;
  position: absolute;
  top: 70px;
  left: 6px;
}
.box1 {
  display: flex;
  position: relative;
  width: 100%;
  height: 673px;
  align-items: center;
  padding-top: 50px;
  justify-content: center;
}

.circle, .circle2, .circle-pink, .circle-yellow {
  border-radius: 50%;
  position: absolute;
}

.circle {
  width: 300px;
  height: 300px;
  background: #6b5b95;
  top: 70px;
  left: 6px;
}

.circle2 {
  width: 200px;
  height: 200px;
  background: #88b04b;
  top: 100px;
  right: 50px;
}

.circle-pink {
  width: 150px;
  height: 150px;
  background: #92a8d1 ;
  bottom: 50px;
  left: 200px;
}

.circle-yellow {
  width: 100px;
  height: 100px;
  background: #f7cac9;
  top: 400px;
  right: 200px;
}

.paw {
  width: 50px;
  height: 50px;
  position: absolute;
  top: 200px;
  left: 200px;
  transform: rotate(30deg);
}
.partner2{
  width: 500px;
  height: 500px;
  transform: translateX(-30px);
}
</style>