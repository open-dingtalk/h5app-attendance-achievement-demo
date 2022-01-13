import * as dd from 'dingtalk-jsapi';

export async function getAuthInfoAsync(corpId: string) {
  return await new Promise((resolve, reject) => {
    dd.ready(function () {
      dd.runtime.permission.requestAuthCode({
        corpId,
        // @ts-ignore
        onSuccess: function (authInfo) {
          let data = {
            corpId: corpId,
            code: authInfo.code
          }
          resolve(data)
        },
        onFail: function (err: any) {
          reject(err);
        },
      });
    });
  });
}