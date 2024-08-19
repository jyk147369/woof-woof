import { defineStore } from "pinia";
import axios from 'axios';

export const useProductManagerStore = defineStore('productManager', {
  state: () => ({ productManagerList: [] }),
  actions: {
    async getProductManagerList() {
      let response = await axios.get("http://localhost:8080/product/manager/list");
      this.productManagerList = response.data.result;

      return this.productManagerList;
    },
  },
})