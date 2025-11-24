import React from 'react';
import ReactDOM from 'react-dom/client';
import {Router} from "./presentation/components/Router.tsx";

const rootElement = document.getElementById('root');
if (!rootElement) throw new Error('Failed to find the root element');

ReactDOM.createRoot(rootElement).render(
    <React.StrictMode>
        <Router/>
    </React.StrictMode>
);
