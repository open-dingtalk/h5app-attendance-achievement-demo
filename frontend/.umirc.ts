import { defineConfig } from 'umi';

export default defineConfig({
  nodeModulesTransform: {
    type: 'none',
  },
  devtool: 'source-map',
  history: {
    type: 'hash',
  },
  hash: true,
  // routes: [
  //   { path: '/', component: '@/pages/index' },
  // ],
  routes: [
    {
      path: '/',
      component: '@/layouts',
      routes: [
        { path: '/', component: '@/pages/index' },
        { path: '/attendance_scratch', component: '@/pages/attendance_scratch' },
        { path: '/attendance_list', component: '@/pages/attendance_list' },
      ],
    },
  ],
  fastRefresh: {},
  devServer: {
    host: '0.0.0.0',
    port: 8000,
    // proxy: {
    //   '/dd': {
    //     target: 'http://localhost:8888',
    //     changeOrigin: true,
    //   },
    // },
  },
});
