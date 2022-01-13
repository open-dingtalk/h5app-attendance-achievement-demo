import React from 'react';
import { Button, message } from 'antd'
import { request } from 'umi';
import { SERVER_DOMAIN } from '@/utils/const'

export default function ScratchPage() {
  const [loading, setLoading] = React.useState(false);

  return (
    <div style={{
      position: 'absolute',
      left: 0,
      top: 0,
      bottom: 0,
      right: 0,
      display: 'flex',
      justifyContent: 'center',
      alignItems: 'center'
    }}>
      <Button type="primary" loading={loading} onClick={async () => {
        message.success("数据较多请耐心等待")
        setLoading(true);
        let data = await request(`${SERVER_DOMAIN}/biz/attendance_scratch`, {
          method: 'GET'
        })
        if (data && data.success) {
          message.success("抓取数据成功,请在考勤查询中查看")
        }
        setLoading(false);
      }}>抓取全部门7天内考勤数据</Button>
    </div>
  );
}
