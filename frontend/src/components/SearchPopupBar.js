import Autocomplete from "@mui/material/Autocomplete";
import TextField from "@mui/material/TextField";
import {isNullOrEmpty} from "../services/utils/ValidatorExtensions";
import {useState} from "react";


export default function SearchPopupBar(props) {
    const {
        items = [],
        sx = [],
        placeholder = 'Search',
        displayProperty = null,
        value,
        setValue
    } = props;

    const [inputValue, setInputValue] = useState('');

    function displayItem(item, displayProp) {
        if(isNullOrEmpty(item)){
            return 'Null';
        }
        if(typeof item === 'string'){
            return item;
        }
        if(Array.isArray(item)){
            return item.join(', ');
        }
        if(typeof item === 'object'){
            const itemProps = Object.values(item);

            if(itemProps.length === 0){
                return 'Empty';
            }

            if(typeof itemProps[displayProp] === 'object'){
                return JSON.stringify(itemProps[displayProp]);
            }

            if(itemProps.length === 1 || !item[displayProp]){
                return itemProps[0];
            }

            return item[displayProp];
        }
    }

    return (
        <Autocomplete
            id="search-popup-bar"
            disablePortal
            value={value}
            onChange={(event, newValue) => {
                setValue(newValue);
            }}
            inputValue={inputValue}
            onInputChange={(event, newInputValue) => {
                setInputValue(newInputValue);
            }}
            options={Array.isArray(items) ?  items  : []}
            sx={{...sx}}
            getOptionLabel={option => displayItem(option, displayProperty)}
            renderInput={(params) =>
                <TextField
                    {...params}
                    label={placeholder}
                />}
        />
    );
}