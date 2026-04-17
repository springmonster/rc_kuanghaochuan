<script setup>
import { ref, onMounted } from 'vue'
import { destinationApi } from '../../api/destination'
import { useRouter } from 'vue-router'

const router = useRouter()
const destinations = ref([])
const loading = ref(false)
const error = ref(null)

const loadDestinations = async () => {
  loading.value = true
  error.value = null
  try {
    const response = await destinationApi.getAll()
    destinations.value = response.data
  } catch (e) {
    error.value = e.message || '加载失败'
  } finally {
    loading.value = false
  }
}

const handleEdit = (id) => {
  router.push(`/destinations/${id}/edit`)
}

const handleDelete = async (id) => {
  if (!confirm('确认删除?')) return
  try {
    await destinationApi.delete(id)
    await loadDestinations()
  } catch (e) {
    error.value = e.message || '删除失败'
  }
}

const handleCreate = () => {
  router.push('/destinations/new')
}

onMounted(loadDestinations)
</script>

<template>
  <div class="container">
    <div class="header">
      <h1>Destination 配置</h1>
      <button @click="handleCreate" class="btn-primary">新建</button>
    </div>

    <div v-if="error" class="error">{{ error }}</div>

    <div v-if="loading">加载中...</div>

    <table v-else class="table">
      <thead>
        <tr>
          <th>ID</th>
          <th>名称</th>
          <th>URL</th>
          <th>API Key</th>
          <th>重试次数</th>
          <th>操作</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="dest in destinations" :key="dest.id">
          <td>{{ dest.id }}</td>
          <td>{{ dest.name }}</td>
          <td>{{ dest.url }}</td>
          <td>{{ dest.apiKey || '-' }}</td>
          <td>{{ dest.retryCount }}</td>
          <td>
            <button @click="handleEdit(dest.id)" class="btn-small">编辑</button>
            <button @click="handleDelete(dest.id)" class="btn-danger btn-small">删除</button>
          </td>
        </tr>
        <tr v-if="destinations.length === 0">
          <td colspan="6" class="empty">暂无数据</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<style scoped>
.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.table {
  width: 100%;
  border-collapse: collapse;
}

.table th,
.table td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: left;
}

.table th {
  background-color: #f5f5f5;
}

.error {
  color: red;
  margin-bottom: 10px;
}

.btn-primary {
  background-color: #4CAF50;
  color: white;
  padding: 10px 20px;
  border: none;
  cursor: pointer;
}

.btn-small {
  padding: 5px 10px;
  margin-right: 5px;
  cursor: pointer;
}

.btn-danger {
  background-color: #f44336;
  color: white;
  border: none;
}
</style>
