import React, { createContext, useState } from 'react';

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
    const [isAdmin, setIsAdmin] = useState(false);

    return (
        <AuthContext.Provider value={{ isAdmin, setIsAdmin }}>
            {children}
        </AuthContext.Provider>
    );
};
