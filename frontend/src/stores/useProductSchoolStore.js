import { defineStore } from "pinia";
import axios from 'axios';

export const useProductSchoolStore = defineStore('productSchool', {
  state: () => ({ productSchoolList: [] }),
  actions: {
    async getProductSchoolList() {
      let response = await axios.get("http://localhost:8080/product/school/list");
      this.productSchoolList = response.data.result;

      return this.productSchoolList;
    },
  },
})