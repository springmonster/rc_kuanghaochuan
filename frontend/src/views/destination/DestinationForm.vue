<script setup>
import { ref, onMounted, computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { destinationApi } from '../../api/destination'

const router = useRouter()
const route = useRoute()

const isEdit = computed(() => !!route.params.id)
const destinationId = computed(() => route.params.id)

const form = ref({
  name: '',
  url: '',
  apiKey: '',
  headers: '',
  body: '',
  retryCount: 3
})

const errors = ref({})
const loading = ref(false)
const error = ref(null)

const validate = () => {
  errors.value = {}
  if (!form.value.name.trim()) {
    errors.value.name = '名称不能为空'
  }
  if (!form.value.url.trim()) {
    errors.value.url = 'URL不能为空'
  }
  return Object.keys(errors.value).length === 0
}

const handleSubmit = async () => {
  if (!validate()) return

  loading.value = true
  error.value = null

  try {
    const data = { ...form.value }
    if (isEdit.value) {
      await destinationApi.update(destinationId.value, data)
    } else {
      await destinationApi.create(data)
    }
    router.push('/destinations')
  } catch (e) {
    error.value = e.message || '保存失败'
  } finally {
    loading.value = false
  }
}

const loadDestination = async (id) => {
  loading.value = true
  try {
    const response = await destinationApi.getById(id)
    const dest = response.data
    form.value = {
      name: dest.name || '',
      url: dest.url || '',
      apiKey: dest.apiKey || '',
      headers: dest.headers || '',
      body: dest.body || '',
      retryCount: dest.retryCount || 3
    }
  } catch (e) {
    error.value = e.message || '加载失败'
  } finally {
    loading.value = false
  }
}

const handleCancel = () => {
  router.push('/destinations')
}

onMounted(() => {
  if (isEdit.value) {
    loadDestination(destinationId.value)
  }
})
</script>

<template>
  <div class="container">
    <div class="header">
      <h1>{{ isEdit ? '编辑' : '新建' }} Destination</h1>
    </div>

    <div v-if="error" class="error">{{ error }}</div>

    <form @submit.prevent="handleSubmit" class="form">
      <div class="form-group">
        <label>名称 *</label>
        <input v-model="form.name" type="text" />
        <span v-if="errors.name" class="error-text">{{ errors.name }}</span>
      </div>

      <div class="form-group">
        <label>URL *</label>
        <input v-model="form.url" type="text" placeholder="https://api.example.com" />
        <span v-if="errors.url" class="error-text">{{ errors.url }}</span>
      </div>

      <div class="form-group">
        <label>API Key</label>
        <input v-model="form.apiKey" type="text" />
      </div>

      <div class="form-group">
        <label>Headers</label>
        <textarea v-model="form.headers" rows="3" placeholder="Content-Type: application/json"></textarea>
      </div>

      <div class="form-group">
        <label>Body 模板</label>
        <textarea v-model="form.body" rows="5" placeholder='{"user_id": "{{user_id}}"}'></textarea>
      </div>

      <div class="form-group">
        <label>重试次数</label>
        <input v-model.number="form.retryCount" type="number" min="1" max="10" />
      </div>

      <div class="form-actions">
        <button type="submit" class="btn-primary" :disabled="loading">
          {{ loading ? '保存中...' : '保存' }}
        </button>
        <button type="button" @click="handleCancel" class="btn-secondary">取消</button>
      </div>
    </form>
  </div>
</template>

<style scoped>
.container {
  max-width: 600px;
  margin: 0 auto;
  padding: 20px;
}

.header {
  margin-bottom: 20px;
}

.form-group {
  margin-bottom: 15px;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
}

.form-group input,
.form-group textarea {
  width: 100%;
  padding: 8px;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.error-text {
  color: red;
  font-size: 12px;
}

.error {
  color: red;
  margin-bottom: 10px;
}

.form-actions {
  display: flex;
  gap: 10px;
}

.btn-primary {
  background-color: #4CAF50;
  color: white;
  padding: 10px 20px;
  border: none;
  cursor: pointer;
}

.btn-primary:disabled {
  background-color: #ccc;
}

.btn-secondary {
  background-color: #9e9e9e;
  color: white;
  padding: 10px 20px;
  border: none;
  cursor: pointer;
}
</style>
