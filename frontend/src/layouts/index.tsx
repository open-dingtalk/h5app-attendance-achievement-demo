import * as React from 'react';
import { Menu, Avatar, Image, Result, Tooltip } from 'antd';
import { history, useModel, withRouter } from 'umi';
import { RouteComponentProps } from 'react-router';
import { createFromIconfontCN } from '@ant-design/icons';
import styles from './index.less';

const { SubMenu } = Menu;

interface BasicLayoutProps extends RouteComponentProps {
  children: any;
}

const MyIcon = createFromIconfontCN({
  scriptUrl:
    'https://chartmo.oss-cn-hangzhou.aliyuncs.com/font_1958573_zrrkd1cniin.js',
});

function BasicLayout(props: BasicLayoutProps) {
  const { initialState } = useModel('@@initialState');
  const [loading, setLoading] = React.useState(true);
  const [username, setUsername] = React.useState('');
  const [avatar, setAvatar] = React.useState('');
  const [error, setError] = React.useState(false);
  const [errType, setErrType] = React.useState<string | undefined>(undefined);
  const [activeKey, setActiveKey] = React.useState('4');

  React.useEffect(() => {
    console.log(initialState);
    if (initialState) {
      let ok = initialState.ok;
      let username = initialState.username;
      let avatar = initialState.avatar;
      setUsername(username);
      setAvatar(avatar);
      if (ok) {
        setLoading(false);
      } else {
        setLoading(false);
      }
    }

    let location = props.location;
    let pathname = location.pathname;
    if (pathname === null || pathname === undefined || pathname === '') {
      setActiveKey('/home');
      history.push('/home');
      return;
    }
    setActiveKey(pathname);
    return () => {};
  }, []);

  function handleSelectedKeys(item: any) {
    setActiveKey(item.key);
  }

  function handleMenuClick({ key }: any) {
    history.push(key);
    setActiveKey(key);
  }

  if (loading) {
    return (
      <div style={{
        position: 'absolute',
        top: 0,
        left: 0,
        bottom: 0,
        right: 0,
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        fontSize: '14px',
      }}>加载中......</div>
    );
  }

  return (
    <div className={styles.pgSecurityLayout}>
      <div className={styles.header} style={{
        borderBottom: '1px solid #e1e1e1'
      }}>
        <div style={{
          position: 'absolute',
          top: 0,
          right: 0,
          bottom: 0,
          width: '80px',
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'center',
          // color: 'red',
          // fontWeight: 700,
        }}>
          <Tooltip placement="bottomRight" title={username}>
            <Avatar src={avatar} />
          </Tooltip>
        </div>
      </div>
      <div className={styles.body}>
        
        <aside className={styles.bodyAside}>
          <Menu
            mode="inline"
            selectedKeys={[activeKey]}
            defaultOpenKeys={['/workbench']}
            style={{ width: '150px', height: 'calc(100vh - 40px)' }}
            onSelect={handleSelectedKeys}
          >
            <Menu.Item key="/attendance_scratch" onClick={handleMenuClick}>
              考勤采集
            </Menu.Item>
            <Menu.Item key="/attendance_list" onClick={handleMenuClick}>
              考勤查询
            </Menu.Item>
          </Menu>
        </aside>
        <section style={{ width: '100%', position: 'relative' }}>
          {
            loading ? <div>haha</div> : props.children
          }
        </section>
      </div>
    </div>
  );
}

export default withRouter(BasicLayout);
