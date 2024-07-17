import { defineStore } from "pinia";
import axios from 'axios';
import VueJwtDecode from "vue-jwt-decode";

const backend = "http://localhost:8080";

const accessToken = localStorage.getItem("accessToken");
const refreshToken = localStorage.getItem("refreshToken");

export const useProductStore = defineStore('product', {
  state: () => ({
    productCeoList: [],
    isTokenExpired: false,
   }),
  actions: {
    async getProductCeoList() {
      // let response = await axios.get("http://www.woofwoof.kro.kr/api/productCeo/listCeo");
      let response = await axios.get("http://localhost:8080/product/school/list");
      this.productCeoList = response.data.result;

      
      return this.productCeoList;
    },
    validateToken() {
      if (accessToken) {
        const decodedAccessToken = VueJwtDecode.decode(accessToken);
        const expirationTime = decodedAccessToken.exp;
        const currentTime = Math.floor(Date.now() / 1000);

        if (expirationTime - currentTime < 30) {
          this.isTokenExpired = true;
        } else {
          this.isTokenExpired = false;
        }
      }
    },
    // 북마크 추가
    async createBookmark(accessToken, requestBody) {
      try {
        this.validateToken();

        const headers = this.isTokenExpired
          ? {
              Authorization: `Bearer ${accessToken}`,
              RefreshToken: `Bearer ${refreshToken}`,
              "Content-Type": "application/json",
            }
          : {
              Authorization: `Bearer ${accessToken}`,
              "Content-Type": "application/json",
            };

        let response = await axios.post(
          backend + "/bookmark/create",
          requestBody,
          {
            headers,
          }
        );
        if (response.headers["new-refresh-token"] != null) {
          if (
            response.headers["new-refresh-token"] !=
            localStorage.getItem("refreshToken")
          ) {
            localStorage.setItem("refreshToken", "");
            localStorage.setItem(
              "refreshToken",
              response.headers["new-refresh-token"]
            );
          }
        }

        if (response.headers["new-access-token"] != null) {
          if (
            response.headers["new-access-token"] !=
            localStorage.getItem("accessToken")
          ) {
            localStorage.setItem("accessToken", "");
            localStorage.setItem(
              "accessToken",
              response.headers["new-access-token"]
            );
          }
        }

        return response;
      } catch (e) {
        console.error("즐겨찾기 등록 실패", e);
        throw e;
      }
    },
    // async checkBookmark(accessToken, boardIdx) {
    //   try {
    //     this.validateToken();

    //     const headers = this.isTokenExpired
    //       ? {
    //           Authorization: `Bearer ${accessToken}`,
    //           RefreshToken: `Bearer ${refreshToken}`,
    //           "Content-Type": "application/json",
    //         }
    //       : {
    //           Authorization: `Bearer ${accessToken}`,
    //           "Content-Type": "application/json",
    //         };
    //     let response = await axios.get(
    //       `${backend}/boardscrap/check/${boardIdx}`,
    //       {
    //         headers,
    //       }
    //     );
    //     if (response.headers["new-refresh-token"] != null) {
    //       if (
    //         response.headers["new-refresh-token"] !=
    //         localStorage.getItem("refreshToken")
    //       ) {
    //         localStorage.setItem("refreshToken", "");
    //         localStorage.setItem(
    //           "refreshToken",
    //           response.headers["new-refresh-token"]
    //         );
    //       }
    //     }

    //     if (response.headers["new-access-token"] != null) {
    //       if (
    //         response.headers["new-access-token"] !=
    //         localStorage.getItem("accessToken")
    //       ) {
    //         localStorage.setItem("accessToken", "");
    //         localStorage.setItem(
    //           "accessToken",
    //           response.headers["new-access-token"]
    //         );
    //       }
    //     }

    //     this.isScrapped = response.data.result.status;

    //     return response;
    //   } catch (e) {
    //     console.error(e);
    //     throw e;
    //   }
    // },
    // 즐겨찾기 취소
    async cancelBookmark(accessToken, bookmarkIdx) {
      try {
        this.validateToken();

        const headers = this.isTokenExpired
          ? {
              Authorization: `Bearer ${accessToken}`,
              RefreshToken: `Bearer ${refreshToken}`,
              "Content-Type": "application/json",
            }
          : {
              Authorization: `Bearer ${accessToken}`,
              "Content-Type": "application/json",
            };

        await axios.patch(
          `${backend}/bookmark/delete/${bookmarkIdx}`,
          {},
          {
            headers,
          }
        );
      } catch (e) {
        console.error(e);
        throw e;
      }
    },
  },
})