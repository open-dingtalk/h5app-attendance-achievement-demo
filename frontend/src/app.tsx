import { message } from 'antd'
import { getAuthInfoAsync } from '@/utils/dingtalk';
import { SERVER_DOMAIN } from '@/utils/const';
import {request} from 'umi';

export async function getInitialState() {
  let ok = false;
  let username = 'unknown';
  let avatar = undefined;
  let gm = message.info('正在尝试免登，获取用户信息')
  let corpId = await request(`${SERVER_DOMAIN}/auth/getCorpId`, {
    method: 'GET'
  });
  //setTimeout(gm, 0);
  // gm = message.info('正在身份验证，获取授权code');
  let result = await getAuthInfoAsync(corpId) as any;
  // setTimeout(gm, 0);
  if (result && result.corpId && result.code) {
    // gm = message.info('正在尝试免登，获取用户信息');
    let loginData = await request(`${SERVER_DOMAIN}/auth/login?authCode=${result.code}`);
    if (loginData && loginData.success) {
      let userId = loginData.data.userId;
      username = loginData.data.userName;
      avatar = loginData.data.avatar;
      message.success('登录成功');
      setTimeout(gm, 0);
      // alert('登录成功，你好' + userName);
      ok = true;
    }
  }

  return {
    ok: ok,
    username: username,
    avatar
  };
}
