import { Routes, Route } from 'react-router-dom';
import {AuthProvider} from "./components/AuthContext.tsx";
import ProtectedRoute from "./components/ProtectedRoute.tsx";
import HomePage from "./components/HomePage.tsx";
import LoginForm from "./components/LoginForm.tsx";

function App() {
    return (
        <AuthProvider>
            <Routes>
                <Route path="/" element={<LoginForm />} />
                <Route
                    path="/home"
                    element={
                        <ProtectedRoute>
                            <HomePage />
                        </ProtectedRoute>
                    }
                />
            </Routes>
        </AuthProvider>
    );
}
//
// ---------------------------------------------------------------------
// import { useEffect, useState } from 'react';

// function App() {
//     const [message, setMessage] = useState("");

//     useEffect(() => {
//         fetch("http://localhost:8080/test/save", {
//     credentials: 'include'
//             })

//             .then((res) => res.json())
//             .then((data) => setMessage(data.content))
//             .catch((err) => console.error("Eroare la fetch:", err));
//     }, []);

//     return <h1>{message}</h1>;
// }

export default App;
