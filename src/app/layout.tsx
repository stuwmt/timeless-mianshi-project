"use client";
import {Geist, Geist_Mono} from "next/font/google";
import "./index.css";
import {AntdRegistry} from "@ant-design/nextjs-registry";
import BasicLayout from "@/layouts/BasicLayout";
import store, {AppDispatch} from '@/stores'
import {Provider, useDispatch} from "react-redux";
import {useCallback, useEffect} from "react";
import {getLoginUserUsingGet} from "@/api/userController";
import {setLoginUser} from "@/stores/loginUser";
import AccessLayout from "@/access/AccessLayout";
import ACCESS_ENUM from "@/access/accessEnum";

const geistSans = Geist({
    variable: "--font-geist-sans",
    subsets: ["latin"],
});

const geistMono = Geist_Mono({
    variable: "--font-geist-mono",
    subsets: ["latin"],
});
/**
 * 执行初始化逻辑的布局（多封装一层）
 * @param children
 * @constructor
 */
const InitLayout: React.FC<
    Readonly<{
        children: React.ReactNode;
    }>
> = ({children}) => {
    const dispatch = useDispatch<AppDispatch>()
    /**
     * 全局初始化函数，有全局单次调用的代码，都可以写到这里
     */
    const doInit = useCallback(() => {
        console.log("在认识一切事物之后，人才能认识自己，因为事物仅仅是人的界限。");
    }, []);

    const doInitLoginUser = useCallback(async () => {
        const res = await getLoginUserUsingGet()
        if (res.data) {
            dispatch(setLoginUser(res.data));
        } else {
            setTimeout(() => {
                const testUser = {
                    userName: "测试登录", id: 1,
                    userRole: ACCESS_ENUM.ADMIN
                };
                dispatch(setLoginUser(testUser));
            }, 3000);
        }
    }, [])

    // 只执行一次
    useEffect(() => {
        doInit();
        doInitLoginUser()
    }, []);

    return <>{children}</>;
};


export default function RootLayout({
                                       children,
                                   }: Readonly<{
    children: React.ReactNode;
}>) {
    return (
        <html lang="zh">
        <body className={`${geistSans.variable} ${geistMono.variable}`}>
        <AntdRegistry>
            <Provider store={store}>
                <InitLayout>
                    <BasicLayout>
                        <AccessLayout>
                            {children}
                        </AccessLayout>
                    </BasicLayout>
                </InitLayout>
            </Provider>
        </AntdRegistry>
        </body>
        </html>
    )
}
