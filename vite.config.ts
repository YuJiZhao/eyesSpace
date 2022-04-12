import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import prismjs from 'vite-plugin-prismjs';

const path = require('path')

export default defineConfig({
  publicDir: path.join(__dirname, `src/public/`),
  plugins: [
    vue(),
    prismjs({
      languages: ['markup', 'css', 'javascript', 'cpp', 'docker', 'git', 'go', 'java', 'javadoc', 'json', 'kotlin', 'less', 'lua', 'markdown', 'mongodb', 'perl', 'php', 'sass', 'scss', 'sql', 'swift', 'typescript'],
    }),
  ],
  optimizeDeps: {
    include: ['@kangc/v-md-editor/lib/theme/vuepress.js'],
  },
  resolve: {
    alias: {
      '@': path.resolve(__dirname, 'src')
    }
  },
  server: {
    host: '0.0.0.0',
    proxy: {
      '/api': {
        target: 'http://127.0.0.1:8080',
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, ''),
      }
    },
  },
})