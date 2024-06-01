<h1 align="center">
반려동물 케어 서비스

Woof </h1>


<div align="center">
<img src="main.png" width="600" height="600"/>



> [플레이 데이터] 한화시스템 BEYOND SW캠프 / Woof


🎬[Demo 시연영상](https://www.youtube.com/watch?v=dhMrKTwNI8U&lc=UgzCJR3WxkvsckRyyO94AaABAg&ab_channel=%EB%94%B0%EB%9D%BC%ED%95%98%EB%A9%B4%EC%84%9C%EB%B0%B0%EC%9A%B0%EB%8A%94IT)   
📃[프로젝트 회고록](블로그주소) 

<br>

## ✨ 프로젝트 설명

```sh
반려동물 시장과 1인 가구의 규모가 점점 커지면서 반려동물 서비스가 많아지고 있다.
하지만 1인 가구에서 시간을 내어 이동하고 맡기고 데리고 오고 하는 것은 제약이 크다.
Woof는 사용자 주변 반려동물 업체의 정보를 제공하고 예약 및 1:1 픽업 서비스를 제공하고자 한다.
```

## 📌 프로젝트 목표

```sh
MYSQL, LINUX, JAVA, SPRING BOOT, JPA 등의 기술을 이용해
Ceo(반려동물 업체) / Manager(직원) / Member(소비자) 이용자가 회원가입 및 로그인, 회원정보수정, 조회, 탈퇴가 가능하도록 구현하였다.
로그인 후 Ceo와 Manager는 상품을 등록/조회/수정/삭제 할 수 있고,
Member는 Cart기능을 통해 즐겨찾기를 할 수 있고, Orders 기능을 통해 상품을 주문 할 수 있다.
주문을 한 사용자(Member)는 Review를 작성, 수정, 조회, 삭제가 가능할 것이다.
```

## 프로젝트 설계
<img src="SystemArchitecture.png" width="800" height="600"/>


## 🔍 요구사항 명세서

<details>
<summary>자세히 보기(클릭)</summary>

<!-- 일단 옛날꺼 복붙한거임 -->
[1] 사용자

1. Woof에 회원가입을 할 수 있다
   ->ID, PW, e-mail을 입력해야 한다
2. 로그인을 할 수 있다
3. 아이디는 이메일로 비밀번호는 대문자, 소문자, 특수문자를 사용하여 10자리 이상으로 설정한다
4. 원하는 시간대에 예약을 할 수 있다
   ->1시간 단위로 예약이 가능하고 중복 선택이 가능하다
5. 각 서비스 마다 요구사항을 작성할 수 있다

[2] 업체(유치원, 병원, 미용실)
1. 업체 등록을 할 수 있다
   ->ID, PW, e-mail, 사업자등록증을 입력해야한다
2. 예약한 고객 정보를 확인하고 관리할 수 있다.

[3] Woof 매니저
1. 우프는 매니저의 경력, 차량 번호, 사원번호를 등록할 수 있다
2. 사용자는 반려동물의 상태에 따라 매니저의 성별을 선택할 수 있고 미선택시 랜덤배정이 된다
3. 고객의 정보를 확인하고 매니저를 배정한다

[4] 예약
1. 고객은 시간, 픽업장소를 선택할 수 있다
2. 고객은 가게 책임자의 ID를 확인할 수 있다
3.  사용자들은 예약번호와 시간, 매니저의 식별번호를 확인할 수 있다
4. 예약 내역은 예약 시간, 처치내용, 방문업체를 사용자로부터 수집한다.

[5] 리뷰
1. 사용자는 이용한 업체에 대한 리뷰 작성과 사진 첨부가 가능하다
2. 사용자는 리뷰 열람에 대한 권한 설정을 할 수 있다.
3. 업체는 고객의 리뷰 번호와 별점, 사진을 확인할 수 있다
4. 업체는 고객의 리뷰에 댓글을 작성할 수 있다
5. 리뷰 작성 시 최소 10글자 이상, 사진 선택, 별점 필수
6. 별점이 일정 시간 이상 꾸준히 낮다면 업체는 상위 노출이 되지 않게 패널티를 부여 받는다


[6] 결제
1. woof는 사용자에게 결제를 요청할 수 있다

[7] 반려동물 등록
1. 고객은 반려동물의 상태를 등록할 수 있다
2. 고객은 반려동물의 나이, 보유질환, 반려동물 등록번호를
3. 업체는 고객의 반려동물의 정보를 확인할 수 있다.
</details>


## 기능 명세서
[swagger-ui](http://192.168.0.100:8080/swagger-ui/#/)



## API 명세서
<p align="center">
  <img src="https://github.com/beyond-sw-camp/be02-2nd-woof-animal_careservice/assets/148935493/b265c2ef-1383-4cf2-b507-4367107f9d9a">
</p>

## 테스트 및 결과

<details>
<summary>자세히 보기(클릭)</summary>
회원 기능
가입
<p align="center">
  <img src="https://github.com/beyond-sw-camp/be02-2nd-woof-animal_careservice/assets/148935493/bb168955-8613-43fb-970c-da635dcdd527
">
</p>
로그인
<p align="center">
  <img src="https://github.com/beyond-sw-camp/be02-2nd-woof-animal_careservice/assets/148935493/d19aa970-b6d9-4602-9281-155655ebac37">
</p>

상품 기능

등록
<p align="center">
  <img src="https://github.com/beyond-sw-camp/be02-2nd-woof-animal_careservice/assets/148935493/7fc33816-fc85-4065-95f9-2deb7b9ccc76">
</p>
<p align="center">
  <img src="https://github.com/beyond-sw-camp/be02-2nd-woof-animal_careservice/assets/148935493/5015fa11-a74a-4734-bb16-ab4dbd1b9d8a">
</p>

조회 (리스트)
<p align="center">
  <img src="https://github.com/beyond-sw-camp/be02-2nd-woof-animal_careservice/assets/148935493/80ab30fe-3cae-46fa-b979-c9689f32ee29">
</p>

조회 (특정 상품)
<p align="center">
  <img src="https://github.com/beyond-sw-camp/be02-2nd-woof-animal_careservice/assets/148935493/8ab54829-7569-4adb-9137-1adb51840f98">
</p>

수정
<p align="center">
  <img src="https://github.com/beyond-sw-camp/be02-2nd-woof-animal_careservice/assets/148935493/ff10fcf1-757b-4fb7-bac0-6b20c3356007   ">
</p>
<p align="center">
  <img src="https://github.com/beyond-sw-camp/be02-2nd-woof-animal_careservice/assets/148935493/6736dbcb-6045-4ded-b2b4-7ec51b1cf8ad">
</p>

삭제
<p align="center">
  <img src="https://github.com/beyond-sw-camp/be02-2nd-woof-animal_careservice/assets/148935493/df300e24-6095-4c87-830f-f6173b09fdea">
</p>
<p align="center">
  <img src="https://github.com/beyond-sw-camp/be02-2nd-woof-animal_careservice/assets/148935493/36968154-d8be-4742-a90e-d8779d2e4b9c">
</p>

리뷰 기능
생성
<p align="center">
  <img src="https://github.com/beyond-sw-camp/be02-2nd-woof-animal_careservice/assets/148935493/473d1a85-ba2b-419c-a4c3-1d3079fa4267">
</p>
조회
<p align="center">
  <img src="https://github.com/beyond-sw-camp/be02-2nd-woof-animal_careservice/assets/148935493/d2677e55-155a-4587-a351-905ab252f98f">
</p>
수정
<p align="center">
  <img src="https://github.com/beyond-sw-camp/be02-2nd-woof-animal_careservice/assets/148935493/a41bd5ac-7a5a-4792-9985-d7ebba7d7535">
</p>
삭제
<p align="center">
  <img src="https://github.com/beyond-sw-camp/be02-2nd-woof-animal_careservice/assets/148935493/40b1513d-0943-4e26-95a3-fa0768f278fb">
</p>
리뷰 삭제 후 조회
<p align="center">
  <img src="https://github.com/beyond-sw-camp/be02-2nd-woof-animal_careservice/assets/148935493/9c25d707-353b-4872-ab61-266013ba41f1
">
</p>

주문 기능
등록
<p align="center">
  <img src="https://github.com/beyond-sw-camp/be02-2nd-woof-animal_careservice/assets/148935493/384dfaf5-84dc-4707-b88e-2dcf315315df">
</p>
조회 (리스트)
<p align="center">
  <img src="https://github.com/beyond-sw-camp/be02-2nd-woof-animal_careservice/assets/148935493/36cd2a51-3e71-48a6-a397-06ebfde29686">
</p>
조회 (특정 주문)
<p align="center">
  <img src="https://github.com/beyond-sw-camp/be02-2nd-woof-animal_careservice/assets/148935493/91c0045a-7fe0-474a-b4d5-a9360f255520">
</p>
수정
<p align="center">
  <img src="https://github.com/beyond-sw-camp/be02-2nd-woof-animal_careservice/assets/148935493/03fc3bdc-a962-462f-a6ce-714e37c876aa">
</p>
삭제
<p align="center">
  <img src="https://github.com/beyond-sw-camp/be02-2nd-woof-animal_careservice/assets/148935493/a48c7341-d0ff-4f3b-b10b-9574ab117016">
</p>

카트 기능
추가
<p align="center">
  <img src="https://github.com/beyond-sw-camp/be02-2nd-woof-animal_careservice/assets/148935493/485f7fcb-0584-4376-8f96-b576feb5c32d">
</p>
<p align="center">
  <img src="https://github.com/beyond-sw-camp/be02-2nd-woof-animal_careservice/assets/148935493/637ff64f-5120-4c65-8634-345937a1a078">
</p>
삭제
<p align="center">
  <img src="https://github.com/beyond-sw-camp/be02-2nd-woof-animal_careservice/assets/148935493/4295d3b0-6866-479b-bd9e-831db4c6da10">
</p>
<p align="center">
  <img src="https://github.com/beyond-sw-camp/be02-2nd-woof-animal_careservice/assets/148935493/380a1431-e28c-4f2a-b28e-0ab35785040b">
</p>


결제 기능


자세한 사진은 Docs/실행결과 폴더 확인해주세요.

</details>



## 🤼‍♂️팀원

**👽️팀원 : 강문혜**

**✨️팀원 : 강지흔**

**💚️팀원 : 김주연**

**🙂️팀원 : 김지은**

**🚀️팀원 : 이창훈**
