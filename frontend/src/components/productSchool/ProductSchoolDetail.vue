<template>
  <div class="all">
    <div v-if="productSchool" class="product-container">
      <div class="product-image" v-if="files.length > 0">
        <button class="arrow left" @click="previousImage">&#10094;</button>
        <div v-for="(file, index) in files" :key="file.idx"
             :class="['image-item', { active: index === currentImageIndex }]">
          <img :src="file.downloadUrl" :alt="file.originalFilename"/>
        </div>
        <button class="arrow right" @click="nextImage">&#10095;</button>
      </div>
      <div class="product-info">
        <div class="storeName">
          <h1>{{ productSchool.storeName }}</h1>
        </div>
        <div class="productName">
          <p>상품명 : {{ productSchool.productName }}</p>
        </div>
        <div class="price">
          <p>가격 : {{ productSchool.price }} 원</p>
        </div>
        <div class="contents">
          <p>상세 설명 : {{ productSchool.contents }}</p>
        </div>
        <div class="businessNum">
          <p>전화번호 : {{ productSchool.businessNum }}</p>
        </div>
        <!--        <div class="product-button">-->
        <!--          <button @click="updateProduct">수정</button>-->
        <!--          <button @click="deleteProduct">삭제</button>-->
        <!--          <button @click="goToReservation">예약하기</button>-->
        <!--        </div>-->
      </div>
    </div>
    <div v-else>
      <p>상품 정보를 불러오는 중...</p>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      productSchool: null,
      files: [],
      currentImageIndex: 0,
      idx: this.$route.params.idx, // URL 파라미터에서 idx 값을 초기화합니다.
    };
  },
  methods: {
    goToReservation() {
      // 로컬 스토리지에 선택 정보 저장
      localStorage.setItem('storeName', this.productSchool.storeName);
      localStorage.setItem('productSchoolIdx', this.idx);

      // 예약 페이지로 라우팅, URL 쿼리 파라미터에 선택 정보 포함
      this.$router.push({
        path: "/orders/create",
        query: {
          storeName: this.productSchool.storeName,
          productSchoolIdx: this.idx,
        },
      });
    },

    async loadProductInfo() {
      try {
        const productResponse = await axios.get(
            // `http://www.woofwoof.kro.kr/api/productCeo/${this.idx}`
            `http://localhost:8080/product/school/read/${this.idx}`
        );
        if (productResponse.data.isSuccess) {
          this.productSchool = productResponse.data.result
          this.loadFiles();
        } else {
          console.error("상품 정보를 불러오는 데 실패했습니다.");
        }
      } catch (error) {
        console.error("Error:", error);
      }
    },

    async loadFiles() {
      try {
        const response = await axios.get(`http://localhost:8080/product/school/files/${this.idx}`);
        this.files = response.data;
      } catch (error) {
        console.error("파일 목록을 가져오는 중 오류가 발생했습니다.", error);
      }
    },

    previousImage() {
      if (this.currentImageIndex > 0) {
        this.currentImageIndex--;
      }
    },

    nextImage() {
      if (this.currentImageIndex < this.files.length - 1) {
        this.currentImageIndex++;
      }
    }

    // updateProduct() {
    //   const productId = this.idx; // 현재 제품의 ID
    //   window.location.href = `/productCeo/update?idx=${productId}`;
    // },
    // deleteProduct() {
    //   if (confirm("정말로 이 상품을 삭제하시겠습니까?")) {
    //     axios
    //         // .delete(`http://www.woofwoof.kro.kr/api/productCeo/deleteCeo?idx=${this.idx}`)
    //         .delete(`http://localhost:8080/productCeo/deleteCeo?idx=${this.idx}`)
    //         .then(() => {
    //           alert("상품이 성공적으로 삭제되었습니다.");
    //           this.$router.push("/productCeo/list"); // 예를 들어, 상품 목록 페이지로 리디렉션
    //         })
    //         .catch((error) => {
    //           console.error("상품 삭제 중 오류 발생:", error);
    //           alert("상품 삭제 중 오류가 발생했습니다.");
    //         });
    //   }
    // },
  },
  created() {
    if (this.idx) {
      this.loadProductInfo();
      this.loadFiles();
    }
  },
};
</script>

<style scoped>
.all {
  display: flex;
  background-color: #f7f8fa;
  padding: 10px;
}

.product-container {
  display: flex;
  width: 100%;
}

.product-info {
  flex: 2;
  padding-top: 30px;
  padding-left: 100px;
  box-sizing: border-box;
}

.product-details {
  flex: 2;
  padding-left: 10px;
  border-left: 2px solid white; /* 이미지와 텍스트 정보 사이의 경계선 */
}

.storeName {
  font-size: 50px;
  font-weight: 600;
  color: rgb(85, 85, 85);
  margin-bottom: 20px;
  border-bottom: 3px solid #555555;
}

.productName {
  font-size: 30px;
  font-weight: 600;
  color: rgb(85, 85, 85);
  margin-top: 70px;
  margin-bottom: 70px;
}

.businessNum {
  font-size: 30px;
  font-weight: 600;
  color: rgb(85, 85, 85);
  margin-top: 70px;
  margin-bottom: 70px;
}

.price {
  font-size: 30px;
  font-weight: 600;
  color: rgb(85, 85, 85);
  margin-top: 70px;
  margin-bottom: 70px;
}

.contents {
  font-size: 30px;
  font-weight: 600;
  color: rgb(85, 85, 85);
  margin-top: 70px;
  margin-bottom: 90px;
}

.product-image {
  flex: 1;
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
}

.image-item {
  display: none;
  max-width: 300px; /* 최대 너비 설정 */
  height: auto; /* 비율 유지 */
  border: 1px solid #ddd; /* 테두리 추가 */
  border-radius: 4px; /* 모서리 둥글게 */
  padding: 5px; /* 패딩 추가 */
  background-color: white; /* 배경색 설정 */
}

.image-item.active {
  display: block;
}

.arrow {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  background-color: rgba(0, 0, 0, 0.5);
  color: white;
  border: none;
  padding: 10px;
  cursor: pointer;
  border-radius: 50%;
}

.arrow.left {
  left: 10px;
}

.arrow.right {
  right: 10px;
}
</style>
