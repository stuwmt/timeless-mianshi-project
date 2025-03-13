"use client";
import {GithubFilled, LogoutOutlined, SearchOutlined,} from "@ant-design/icons";
import {ProConfigProvider, ProLayout} from "@ant-design/pro-components";
import {Dropdown, Input, theme} from "antd";
import Link from "next/link";
import {usePathname} from "next/navigation";
import React, {useState} from "react";
import Image from "next/image";
import GlobalFooter from "@/components/GlobalFooter";
import './index.css'
import {menus} from "../../../config/menu";
import {useSelector} from "react-redux";
import {RootState} from "@/stores";
import getMenuAccessibleMenus from "@/access/menuAccess";
import MdEditor from "@/components/MdEditor";
import MdViewer from "@/components/MdViewer";

const SearchInput = () => {
    const {token} = theme.useToken();
    return (
        <div
            key="SearchOutlined"
            aria-hidden
            style={{
                display: "flex",
                alignItems: "center",
                marginInlineEnd: 24,
            }}
            onMouseDown={(e) => {
                e.stopPropagation();
                e.preventDefault();
            }}
        >
            <Input
                style={{
                    borderRadius: 4,
                    marginInlineEnd: 12,
                    backgroundColor: token.colorBgTextHover,
                }}
                prefix={
                    <SearchOutlined
                        style={{
                            color: token.colorTextLightSolid,
                        }}
                    />
                }
                placeholder="搜索题目"
                variant="borderless"
            />
        </div>
    );
};

interface Props {
    children: React.ReactNode;
}

export default function BasicLayout({children}: Props) {
    const [text, setText] = useState<string>('');
    const loginUser = useSelector((state: RootState) => state.loginUser);
    {
        const pathname = usePathname();
        return (
            <div
                id="basic-layout"
                style={{
                    height: "100vh",
                    overflow: "auto",
                }}
            >
                <ProConfigProvider hashed={false}>
                    <ProLayout
                        title="面试刷题平台"
                        logo={
                            <Image
                                src="/assets/images/logo.jpg"
                                alt="面试刷题平台"
                                width={38}
                                height={38}
                                priority
                            />
                        }
                        layout="top"
                        prefixCls="my-prefix"
                        location={{
                            pathname,
                        }}
                        token={{
                            header: {
                                colorBgMenuItemSelected: "rgba(0,0,0,0.04)",
                            },
                        }}
                        siderMenuType="group"
                        menu={{
                            collapsedShowGroupTitle: true,
                        }}
                        avatarProps={{
                            src: loginUser.userAvatar || "/assets/images/noLogin.jpg",
                            size: "small",
                            title: loginUser.userName || "未登录",
                            render: (props, dom) => {
                                return (
                                    <Dropdown
                                        menu={{
                                            items: [
                                                {
                                                    key: "logout",
                                                    icon: <LogoutOutlined/>,
                                                    label: "退出登录",
                                                },
                                            ],
                                        }}
                                    >
                                        {dom}
                                    </Dropdown>
                                );
                            },
                        }}
                        actionsRender={(props) => {
                            if (props.isMobile) return [];
                            return [
                                <SearchInput key="search"/>,
                                <a
                                    href="https://github.com/stuwmt"
                                    target="_blank"
                                    key="github"
                                >
                                    <GithubFilled key="GithubFilled"/>
                                </a>,
                            ];
                        }}
                        //渲染头部栏
                        headerTitleRender={(logo, title, _) => {
                            const defaultDom = (
                                <a href={"/"}>
                                    {logo}
                                    {title}
                                </a>
                            );
                            return <>{defaultDom}</>;
                        }}
                        //渲染底部栏
                        footerRender={() => <GlobalFooter/>}
                        onMenuHeaderClick={(e) => console.log(e)}
                        //定义哪些页面需要展示
                        menuDataRender={() =>
                            getMenuAccessibleMenus(loginUser, menus)}
                        // 菜单渲染
                        menuItemRender={(item, dom) => (
                            <Link href={item.path || "/"} target={item.target}>
                                {dom}
                            </Link>
                        )}
                    >
                        <MdEditor value={text} onChange={setText}/>
                        <MdViewer value={text}/>
                        {children}
                    </ProLayout>
                </ProConfigProvider>
            </div>
        );
    }
}
