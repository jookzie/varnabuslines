import {createBrowserRouter} from "react-router-dom";
import {Root} from "../components/Root";
import HomePage from "../components/Pages/HomePage/HomePage";
import ErrorPage from "../components/Pages/ErrorPage";
import LoginPage from "../components/Pages/LoginPage";
import RegisterPage from "../components/Pages/RegisterPage";
import React from "react";
import UsersCrudPage from "../components/Pages/Admin/UserCrudPage/UsersCrudPage";
import TicketsCrudPage from "../components/Pages/Admin/TicketCrudPage/TicketsCrudPage";
import StationCrudPage from "../components/Pages/Admin/StationCrudPage/StationCrudPage";
import BusLineCrudPage from "../components/Pages/Admin/BusLineCrudPage/BusLineCrudPage";
import BusCrudPage from "../components/Pages/Admin/BusCrudPage/BusCrudPage";
import AlertsPage from "../components/Pages/AlertsPage";
import AlertCrudPage from "../components/Pages/Admin/AlertCrudPage/AlertCrudPage";
import StatisticsPage from "../components/Pages/Admin/StatisticsPage/StatisticsPage";

const router = createBrowserRouter([
    {
        path: "/",
        element: <Root/>,
        errorElement: <ErrorPage/>,
        children: [
            {
                path: "/",
                element: <HomePage/>,
            },
            {
                path: "/authentication",
                element: <LoginPage/>,
            },
            {
                path: "/register",
                element: <RegisterPage/>,
            },
            {
                path: "/alerts",
                element: <AlertsPage/>,
            },
            {
                path: "/admin/users",
                element: <UsersCrudPage />,
            },
            {
                path: "/admin/tickets",
                element: <TicketsCrudPage />,
            },
            {
                path: "/admin/buslines",
                element: <BusLineCrudPage/>,
            },
            {
                path: "/admin/buses",
                element: <BusCrudPage/>,
            },
            {
                path: "/admin/stations",
                element: <StationCrudPage/>,
            },
            {
                path: "/admin/alerts",
                element: <AlertCrudPage/>,
            },
            {
                path: "/admin/statistics",
                element: <StatisticsPage/>,
            },
        ]
    }
]);
export default router;