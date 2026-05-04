<template>
  <div>
    <h1>作者頁</h1>

    <ul>
      <!-- 載入中 -->
      <div v-if="loading">
        載入中...
      </div>

      <!-- 找不到 -->
      <div v-else-if="error === 'NOT_FOUND'">
        查無此作者
      </div>

      <!-- 其他錯誤 -->
      <div v-else-if="error">
        發生錯誤
      </div>

      <!-- 成功載入 -->
      <div v-else-if="author">
        <h2>作者ID: {{ author.id }}</h2>
        <h2>作者名稱:{{ author.name }}</h2>
      </div>

    </ul>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from "vue";
import { useRoute } from "vue-router";
import { getAuthorById } from "../api/authorApi";
import type { AuthorResponse } from "../types/author";
import axios from "axios";

const route = useRoute(); // 取得路由資訊
//新增loading、error處理讀取中與錯誤狀態以利於後續處理
const author = ref<AuthorResponse | null>(null);
const loading = ref(true);
const error = ref<string | null>(null);

onMounted(async () => {
  try {
    const id = Number(route.params.id); //從url獲取ID

    const res = await getAuthorById(id); // AuthorById，看看資料長怎樣
    author.value = res;

  } catch (err) { //刻意不寫型別維持unknown
    //判斷是否為404
    if (axios.isAxiosError(err)) {
      const status = err.response?.status;

      switch(status) {
        case 404:
          error.value = "NOT_FOUND";
          break;
        case 500:
          error.value = "SERVER_ERROR";
          break;
        default:
          error.value = "ERROR";
      }
    } else {
      error.value = "UNKNOWN_ERROR";
    }
  } finally {
    loading.value = false; //一定要關掉 loading
  }

});
</script>
