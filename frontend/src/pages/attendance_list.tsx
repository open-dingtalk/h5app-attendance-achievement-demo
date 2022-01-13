import React from 'react';
import {Table} from 'antd'
import {request} from 'umi';
import { SERVER_DOMAIN } from "@/utils/const"

export default function IndexPage() {

  const [loading, setLoading] = React.useState(true);
  const [columns, setColumns] = React.useState([]);
  const [dataSources, setDataSources] = React.useState([]);

  React.useEffect(() => {
    async function fetch() {
      let data = await request(`${SERVER_DOMAIN}/biz/attendances`, {
        method: 'GET'
      }) as any;
      if (data.success) {
        let columns = data.data.columns || [];
        let dataSources = data.data.dataSources || [];
        setColumns(columns);
        setDataSources(dataSources);
      }
  
      setLoading(false);
    }

    fetch();
  }, []);

  return (
    <div>
      <Table
          key={'dstable'}
          loading={loading}
          columns={columns}
          dataSource={dataSources}
          // pagination={pagination}
          scroll={{ y: 360, x: 800 }}
        />
    </div>
  );
}
