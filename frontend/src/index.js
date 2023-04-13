import {ThemeProvider} from '@emotion/react';
import React from 'react';
import ReactDOM from 'react-dom/client';
import {RouterProvider} from "react-router-dom";
import './index.css';
import MyTheme from './local/MyTheme';
import router from "./local/Router";

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <React.StrictMode>
        <ThemeProvider theme={MyTheme}>
            <RouterProvider router={router}/>
        </ThemeProvider>
    </React.StrictMode>
);

