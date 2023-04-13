import {Outlet} from 'react-router-dom';
import NavBar from './NavBar/NavBar';
import GuestToolbar from "./NavBar/toolbars/GuestToolbar";
import * as React from "react";
import {createContext, useEffect, useState} from "react";
import {getParsedToken, Roles} from "../services/authorization/AuthorizationService";
import AdminToolbar from "./NavBar/toolbars/AdminToolbar";
import UserToolbar from "./NavBar/toolbars/UserToolbar";
import SockJS from 'sockjs-client';
import Stomp from 'stompjs';
import Configuration from "../local/Configuration";
import AlertNotification from "./AlertNotification";

export const AlertsContext = createContext(null);

export function Root() {
    const [toolbar, setToolbar] = useState(<GuestToolbar/>);

    useEffect(() => {
        const handleStorage = () => {
            let roles = getParsedToken()?.roles;
            if (roles) {
                if (roles.includes(Roles.ADMIN)) {
                    setToolbar(<AdminToolbar/>);
                } else if (roles.includes(Roles.USER)) {
                    setToolbar(<UserToolbar/>);
                }
            } else {
                setToolbar(<GuestToolbar/>);
            }
        };

        handleStorage();

        window.addEventListener('tokenChanged', handleStorage);
        return () => window.removeEventListener('tokenChanged', handleStorage);
    }, [])

    const [stompClient, setStompClient] = useState(null);

    useEffect(() => {
        // use SockJS as the websocket client
        const socket = SockJS(Configuration.domain + '/ws');
        // Set stomp to use websockets
        const stompClient = Stomp.over(socket);

        /* headers cannot be sent through the sockjs client, unfortunately
        const token = getToken();
        const headers = {};
        if (token) {
            headers['Authorization'] = 'Bearer ' + token;
        }*/
        // connect to the backend
        stompClient.connect({}, () => {
            // subscribe to the backend
            stompClient.subscribe('/topic/alerts', (data) => {
                setActiveAlert(JSON.parse(data.body));
            });
        });
        // maintain the client for sending and receiving
        setStompClient(stompClient);
    }, [setStompClient]);



    const [activeAlert, setActiveAlert] = useState(false);

    return (
        <AlertsContext.Provider value={stompClient}>
            <AlertNotification alert={activeAlert} setAlert={setActiveAlert}/>
            <NavBar toolbar={toolbar}/>
            <Outlet/>
        </AlertsContext.Provider>
    );
}