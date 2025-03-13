import {MenuDataItem} from "@ant-design/pro-layout";
import {CrownOutlined} from "@ant-design/icons";
import ACCESS_ENUM from "@/access/accessEnum";

// 菜单列表
const menus = [
    {
        path: "/",
        name: "主页",
    },
    {
        path: "/banks",
        name: "题库",
    },
    {
        path: "/questions",
        name: "题目",
    },
    {
        path: "/admin",
        name: "管理",
        icon: <CrownOutlined/>,
        access: ACCESS_ENUM.ADMIN,
        children: [
            {
                path: "/admin/user",
                name: "用户管理",
                access: ACCESS_ENUM.ADMIN
            }
        ],
    },
] as MenuDataItem[];
//根据全部路径查找菜单项
const findAllMenuItemByPath = (path: string): MenuDataItem | null => {
    return findMenuItemByPath(menus, path);
}
//根据路径查找菜单项
const findMenuItemByPath = (menus: MenuDataItem[], path: string): MenuDataItem | null => {
    for (const menu of menus) {
        if (menu.path === path) {
            return menu;
        }
        if (menu.children) {
            const result = findMenuItemByPath(menu.children, path);
            if (result) {
                return result;
            }
        }
    }
    return null;
}


// 导出
export {menus, findAllMenuItemByPath}
