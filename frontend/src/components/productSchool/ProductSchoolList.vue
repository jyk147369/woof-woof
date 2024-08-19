<template>
    <div class="button-container">
      <br>
      <button @click="goToProductCreatePage">상품 등록</button>
      <br><br>
    </div>

  <div id="ceo-page-list">
    <ProductSchoolCardComponent v-for="productSchool in productSchoolStore.productSchoolList" :key="productSchool.idx" :productSchool="productSchool"/>
  </div>
</template>


<script>
import { mapStores } from 'pinia'
import { useProductSchoolStore } from '@/stores/useProductSchoolStore';

import ProductSchoolCardComponent from '@/components/productSchool/ProductSchoolCardComponent.vue'
export default {
    name: 'ProductSchoolPage',
    components: {
      ProductSchoolCardComponent
    },
    computed: {
        ...mapStores(useProductSchoolStore)
    },
    mounted() {
        this.productSchoolStore.getProductSchoolList()
    },
    methods: {
      goToProductCreatePage() {
      this.$router.push("/product/school/create");
    },
    }
}
</script>


  
<style>
#ceo-page-list {
    width: 100%;
    overflow: hidden;
    display: grid;
    grid-template-columns: 1fr 1fr 1fr 1fr;
    padding: 0px 0px 100px 0px;
    background-color: #f7f8fa;
}

.button-container {
  text-align: right;
  padding-right: 40px;
  background-color: #F7F8FA;
}
.button-container button{
  font-size:18px;
  font-weight:600;
  padding: 5px 10px;
  color:white;
  letter-spacing:0.2px;
  border: none;
  background-color: #fae14a;
}
.button-container button:hover {
  color: #555555;
}
</style>
  