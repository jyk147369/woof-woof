import { defineStore } from "pinia";
import axios from "axios";
import { toRaw } from "vue";

const backend = "http://localhost:8080";

export const useMemberStore = defineStore("member", {
  state: () => ({ isLoading: false, isLoggedIn: false }),
  actions: {
    async login(memberLogin) {
      this.isLoading = true;
      try {
        console.log("로그인 요청 데이터:", memberLogin);
        let response = await axios.post(
            backend + "/user/login", // 수정된 엔드포인트
            toRaw(memberLogin)
        );
        console.log("로그인 응답 데이터:", response.data);

        // 응답 데이터 구조에 맞게 수정
        if (response.status === 200 && response.data.result.accessToken != null) {
          sessionStorage.setItem("atoken", response.data.result.accessToken);
          this.isLoggedIn = true;
          console.log(response.data.result.idx);
          sessionStorage.setItem("memberIdx", response.data.result.idx);
          return true;
        }
      } catch (e) {
        if (e.response) {
          console.log("에러 응답 데이터:", e.response.data);
        } else {
          console.log("에러:", e.message);
        }
        return false;
      } finally {
        this.isLoading = false;
      }
      return false;
    },
    async signup(memberSignup) {
      this.isLoading = true;
      try {
        let response = await axios.post(
            backend + "/user/member/signup",
            toRaw(memberSignup)
        );
        console.log("회원가입 응답 데이터:", response.data);
        if (response.status === 200 && response.data.isSuccess === true) {
          return true;
        }
      } catch (e) {
        if (e.response) {
          console.log("에러 응답 데이터:", e.response.data);
        } else {
          console.log("에러:", e.message);
        }
        return false;
      } finally {
        this.isLoading = false;
      }
      return false;
    },
  },
});
