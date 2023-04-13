import * as React from 'react';
import TextField from '@mui/material/TextField';
import Autocomplete from '@mui/material/Autocomplete';
import PropTypes from "prop-types";

export default function ComboBox(props) {
    const {options, value, setValue, label} = props;

    const [inputValue, setInputValue] = React.useState('');

    return (
        <Autocomplete
            autoComplete={false}
            value={value}
            onChange={(event, newValue) => {
                setValue(newValue);
            }}
            inputValue={inputValue}
            onInputChange={(event, newInputValue) => {
                setInputValue(newInputValue);
            }}
            id="controllable-states-demo"
            options={options}
            renderInput={(params) => <TextField {...params} label={label} />}
        />
    );
}

ComboBox.propTypes = {
    options: PropTypes.array.isRequired,
    value: PropTypes.string,
    setValue: PropTypes.func.isRequired,
    label: PropTypes.string.isRequired
}