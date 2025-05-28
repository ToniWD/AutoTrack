import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../styles/LoginForm.css'; // Importăm fișierul CSS
import { useAuth } from "./AuthContext.tsx";
import { handleLogin } from '../API/services.tsx';


const LoginForm = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();
    const { login } = useAuth();

    

const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
  e.preventDefault();

  if (!username || !password) {
    setError('Both fields are required');
    return;
  }

  try {
    const success = await handleLogin({username, password});
    if (success) {
      alert('Login successful!');
      login();  // presupun că e funcția ta de context/auth
      navigate('/home');
    }
  } catch (error) {
    setError('Incorrect credentials!');
  }
};


    return (
        <div className="login-form-container">
            <div className="login-form-box">
                <h2 className="login-title">Login</h2>
                <form onSubmit={handleSubmit}>
                    <div className="form-group">
                        <label htmlFor="username" className="label">Username</label>
                        <input
                            type="text"
                            id="username"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                            className="input-field"
                            placeholder="Enter your username"
                            required
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="password" className="label">Password</label>
                        <input
                            type="password"
                            id="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            className="input-field"
                            placeholder="Enter your password"
                            required
                        />
                    </div>
                    {error && <p className="error-message">{error}</p>}
                    <button
                        type="submit"
                        className="submit-button"
                    >
                        Login
                    </button>
                </form>
            </div>
        </div>
    );
};

export default LoginForm;


// import { useState } from 'react';
// import { useNavigate } from 'react-router-dom';
// import '../styles/LoginForm.css';
// import {useAuth} from "./AuthContext.tsx";
//
//
// const LoginForm = () => {
//     const [username, setUsername] = useState('');
//     const [password, setPassword] = useState('');
//     const [error, setError] = useState('');
//     const navigate = useNavigate();
//     const { login } = useAuth();
//
//     const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
//         e.preventDefault();
//
//         if (!username || !password) {
//             setError('Both fields are required');
//             return;
//         }
//
//         if (username === 'admin' && password === 'admin') {
//             alert('Login successful!');
//             login();
//             navigate('/home');
//         } else {
//             setError('Incorrect credentials!');
//         }
//     };
//
//     return (
//         <div className="min-h-screen flex items-center justify-center bg-gray-900 text-white">
//             <div className="w-full max-w-sm p-8 bg-gray-800 shadow-lg rounded-lg">
//                 <h2 className="text-2xl font-semibold text-center mb-6">Login</h2>
//                 <form onSubmit={handleSubmit}>
//                     <div className="mb-4">
//                         <label htmlFor="username" className="block text-sm font-medium text-gray-300">Username</label>
//                         <input
//                             type="text"
//                             id="username"
//                             value={username}
//                             onChange={(e) => setUsername(e.target.value)}
//                             className="mt-1 block w-full px-4 py-2 border border-gray-600 rounded-lg bg-gray-700 text-white focus:ring-green-500 focus:border-green-500"
//                             placeholder="Enter your username"
//                             required
//                         />
//                     </div>
//                     <div className="mb-4">
//                         <label htmlFor="password" className="block text-sm font-medium text-gray-300">Password</label>
//                         <input
//                             type="password"
//                             id="password"
//                             value={password}
//                             onChange={(e) => setPassword(e.target.value)}
//                             className="mt-1 block w-full px-4 py-2 border border-gray-600 rounded-lg bg-gray-700 text-white focus:ring-green-500 focus:border-green-500"
//                             placeholder="Enter your password"
//                             required
//                         />
//                     </div>
//                     {error && <p className="text-red-500 text-sm mt-2">{error}</p>}
//                     <button
//                         type="submit"
//                         className="w-full py-2 bg-green-500 text-white rounded-lg hover:bg-green-600 focus:outline-none focus:ring-2 focus:ring-green-500"
//                     >
//                         Login
//                     </button>
//                 </form>
//             </div>
//         </div>
//     );
// };
//
// export default LoginForm;
