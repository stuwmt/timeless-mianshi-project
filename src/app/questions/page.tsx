"use client";

import {useSelector} from "react-redux";
import {RootState} from "@/stores";

export default function Home() {
    const loginUser = useSelector((state: RootState) => state.loginUser);
    return (
        <div>
            {JSON.stringify(loginUser)}
            题目页面
        </div>
    );
}
