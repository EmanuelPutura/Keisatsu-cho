import React, { useState } from "react";

function useLocalStorage(key, defaultValue) {

    const getItem = () => {
        const itemString = localStorage.getItem(key);
        const userItem = JSON.parse(itemString);
        return userItem || defaultValue;
    };

    const [item, setItem] = useState(getItem());

    const saveItem = userItem => {
        if(userItem === undefined){
            localStorage.removeItem(key);
        } else {
            localStorage.setItem(key, JSON.stringify(userItem));
        }
        setItem(userItem);
    }

    return{
        setItem: saveItem,
        item
    }
}

export default useLocalStorage;