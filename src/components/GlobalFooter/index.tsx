import './index.css'
import React from "react";

/**
 * 全局页脚
 * @constructor
 */
export default function GlobalFooter() {
    const currentYear = new Date().getFullYear();
    {
        return (
            <div className="global-footer">
                <div>© {currentYear} 面试刷题平台</div>
                <div>by 星如雨</div>
            </div>
        );
    }
}
