import SearchIcon from "@mui/icons-material/Search";
import * as React from "react";
import {styled} from "@mui/system";
import {InputBase} from "@mui/material";
import {isNullOrEmpty} from "../services/utils/ValidatorExtensions";


export default function SearchBar({sx, inputWidth, itemsSource, setOutput}) {
    function filterArray(array, string) {
        if(isNullOrEmpty(...arguments))
            return array;

        if(!Array.isArray(array)){
            console.error('First argument must be an array.');
            return array;
        }

        if(array.length === 0)
            return array;

        return array.filter(item => {
            if(isNullOrEmpty(item))
                return false;
            if (typeof item === 'object') {
                return Object.values(item).some(val => val.toString().includes(string));
            } else {
                // Check if the string is contained in the item itself
                return item?.includes(string);
            }
        });
    }

    function handleChange(event) {
        const string = event.target.value;
        if (isNullOrEmpty(string)) {
            return;
        }

        const result = filterArray(itemsSource, string);
        setOutput(result);
    }

    return (
        <Search
            sx={{
                border: 2,
                borderColor: 'primary.main',
                borderRadius: 3,
                ...sx
        }}>
            <SearchIconWrapper>
                <SearchIcon sx={{
                    borderRight: 1,
                    borderColor: 'primary.main',
                }}/>
            </SearchIconWrapper>
            <StyledInputBase
                placeholder="Search…"
                inputProps={{'aria-label': 'search'}}
                sx={{
                    width: inputWidth,
                }}
                onChange={handleChange}
            />
        </Search>
    );
}

const Search = styled('div')(({theme}) => ({
    position: 'relative',
    borderRadius: theme.shape.borderRadius,
    backgroundColor: '#ffffff',
    '&:hover': {
        backgroundColor: '#f0f0f0',
    },
    marginLeft: 0,
    width: 'fit-content',
    [theme.breakpoints.up('sm')]: {
        width: 'auto',
    },
}));

const SearchIconWrapper = styled('div')(({theme}) => ({
    padding: theme.spacing(0, 2),
    height: '100%',
    position: 'absolute',
    pointerEvents: 'none',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
}));

const StyledInputBase = styled(InputBase)(({theme, onchange}) => ({
    onchange: onchange,
    color: 'inherit',
    width: '100%',
    '& .MuiInputBase-input': {
        padding: theme.spacing(1, 1, 1, 0),
        // vertical padding + font size from searchIcon
        paddingLeft: `calc(1em + ${theme.spacing(4)})`,
        transition: theme.transitions.create('width'),
    },
}));
