import {menus} from "../../config/menu";
import checkAccess from "@/access/checkAccess";

const getMenuAccessibleMenus = (loginUser: API.LoginUserVO, menuItems = menus) => {
    return menuItems.filter(
        (menuItem) => {
            if (!checkAccess(loginUser, menuItem.access)) {
                return false;
            }
            if (menuItem.children) {
                menuItem.children = getMenuAccessibleMenus(loginUser, menuItem.children);
            }
            return true;
        }
    )
}
export default getMenuAccessibleMenus;
