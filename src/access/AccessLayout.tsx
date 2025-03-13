import React from "react";
import {usePathname} from "next/navigation";
import {useSelector} from "react-redux";
import {RootState} from "@/stores";
import {findAllMenuItemByPath} from "../../config/menu";
import ACCESS_ENUM from "@/access/accessEnum";
import checkAccess from "@/access/checkAccess";
import Forbidden from "@/app/forbidden";

const AccessLayout: React.FC<Readonly<{
    children: React.ReactNode
}>> = ({children}) => {
    const pathName = usePathname();
    //当前登录用户
    const loginUser = useSelector((state: RootState) => state.loginUser);
    //获取路径权限
    const menu = findAllMenuItemByPath(pathName);
    const needAccess = menu?.access || ACCESS_ENUM.NOT_LOGIN;
    //检查权限
    const canAccess = checkAccess(loginUser, needAccess);
    if (!canAccess) {
        return <Forbidden/>;
    }
    return <>{children}</>;
};


export default AccessLayout;
