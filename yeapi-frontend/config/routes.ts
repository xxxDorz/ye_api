export default [
  { name: '登录',path: '/user', layout: false, routes: [{ path: '/user/login', component: './User/Login' }] },
  //{ name: '欢迎页面',path: '/welcome', icon: 'smile', component: './Welcome' },

  {
    path: '/admin',
    name: '管理页',
    access: 'canAdmin',
    icon: 'crown',
    routes: [
      { name:'接口管理',icon: 'table', path: '/admin/interface_info', component: './Admin/InterfaceInfo' },
    ],
  },

  //{ path: '/', redirect: '/welcome' },
  { path: '*', layout: false, component: './404' },
];




// export default [
//   { name: '登录',path: '/user', layout: false, routes: [{ path: '/user/login', component: './User/Login' }] },
//   { name: '欢迎页面',path: '/welcome', icon: 'smile', component: './Welcome' },
//   {
//     path: '/admin',
//     icon: 'crown',
//     access: 'canAdmin',
//     name: '管理页',
//     routes: [
//       { name:'二级目录',path: '/admin', redirect: '/admin/sub-page' },
//       { path: '/admin/sub-page', component: './Admin' },
//     ],
//   },
//   { name:'查询表格',icon: 'table', path: '/list', component: './InterfaceInfo' },
//   { path: '/', redirect: '/welcome' },
//   { path: '*', layout: false, component: './404' },
// ];
