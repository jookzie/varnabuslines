// Reference: https://mui.com/material-ui/react-transfer-list/

import * as React from 'react';
import {useEffect, useState} from 'react';
import Grid from '@mui/material/Grid';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import Checkbox from '@mui/material/Checkbox';
import Button from '@mui/material/Button';
import Paper from '@mui/material/Paper';
import PropTypes from 'prop-types';
import Pagination from "@mui/material/Pagination";
import SearchPopupBar from "./SearchPopupBar";

function not(a, b) {
    return a.filter((value) => b.indexOf(value) === -1);
}

function intersection(a, b) {
    return a.filter((value) => b.indexOf(value) !== -1);
}

export default function TransferList(props) {
    const {
        excluded,
        setExcluded,
        included,
        setIncluded,
        propertyToDisplay,
        orderByProperty,
        rowsPerPage = 6
    } = props;


    const [checked, setChecked] = useState([]);
    const [leftPage, setLeftPage] = useState(1);
    const [rightPage, setRightPage] = useState(1);
    const [leftSearchValue, setLeftSearchValue] = useState(null);
    const [rightSearchValue, setRightSearchValue] = useState(null);

    const excludedChecked = intersection(checked, excluded);
    const includedChecked = intersection(checked, included);


    
    useEffect(() => {
        if(!excluded.includes(leftSearchValue)) {
            setLeftSearchValue(null);
        }
        if(!included.includes(rightSearchValue)) {
            setRightSearchValue(null);
        }
    }, [excluded, included]);

    const handleToggle = (value) => () => {
        const currentIndex = checked.indexOf(value);
        const newChecked = [...checked];

        if (currentIndex === -1) {
            newChecked.push(value);
        } else {
            newChecked.splice(currentIndex, 1);
        }

        setChecked(newChecked);
    };

    function handleChangeLeftPage(event, newPage) {
        setLeftPage(newPage);
    }

    function handleChangeRightPage(event, newPage) {
        setRightPage(newPage);
    }

    const handleAllRight = () => {
        setIncluded(included.concat(excluded));
        setExcluded([]);
    };

    const handleCheckedRight = () => {
        setIncluded(included.concat(excludedChecked));
        setExcluded(not(excluded, excludedChecked));
        setChecked(not(checked, excludedChecked));
    };

    const handleCheckedLeft = () => {
        setExcluded(excluded.concat(includedChecked));
        setIncluded(not(included, includedChecked));
        setChecked(not(checked, includedChecked));
    };

    const handleAllLeft = () => {
        setExcluded(excluded.concat(included));
        setIncluded([]);
    };

    function getPageCount(items) {
        if (!items) {
            return 0;
        }
        if (Array.isArray(items)) {
            return Math.ceil(items.length / rowsPerPage);
        }
        return 0;
    }

    const customList = (items, page) => (
        <Paper sx={{
            minWidth: 250,
            height: 350,
            overflow: 'auto',
            border: 1,
            borderRadius: 3}}>
            <List dense component="div" role="list">
                {Array.isArray(items) && items
                    .slice((page - 1) * rowsPerPage, (page - 1) * rowsPerPage + rowsPerPage)
                    .sort((a, b) => orderByProperty ? a[orderByProperty] < b[orderByProperty] ? 1 : -1 : 0)
                    .map((item, index) => {
                        const value = item[propertyToDisplay] || Object.values(item)[0];
                        const labelId = `transfer-list-item-${value}-label`;

                        return (
                            <ListItem
                                key={`list-item-${index}`}
                                role="listitem"
                                onClick={handleToggle(item)}
                            >
                                <ListItemIcon key={`list-item-icon-${index}`}>
                                    <Checkbox
                                        key={`list-item-checkbox-${index}`}
                                        checked={checked.indexOf(item) !== -1}
                                        tabIndex={-1}
                                        disableRipple
                                        inputProps={{
                                            'aria-labelledby': labelId,
                                        }}
                                    />
                                </ListItemIcon>
                                <ListItemText id={labelId} primary={value} key={`list-item-text-${index}`}/>
                            </ListItem>
                        );
                    })}
                <ListItem/>
            </List>
        </Paper>
    );

    return (
        <Grid container spacing={2} justifyContent="center" alignItems="center">
            <Grid item>
                <SearchPopupBar
                    sx={{
                        my:1
                    }}
                    items={excluded}
                    displayProperty={propertyToDisplay}
                    value={leftSearchValue}
                    setValue={setLeftSearchValue}
                />
                <Grid container direction="column" alignItems="left">
                    {/*<SearchBar sx={{mb: 1}} itemsSource={excluded} setOutput={setExcludedFiltered}/>*/}
                    {customList(leftSearchValue ? [leftSearchValue] : excluded, leftPage) /* excludedFiltered*/}
                    <Pagination
                        sx={{ mt: 1}}
                        component="div"
                        count={getPageCount(excluded) /* excludedFiltered*/}
                        rowsperpage={rowsPerPage}
                        page={leftPage}
                        onChange={handleChangeLeftPage}
                    />
                </Grid>
            </Grid>
            <Grid item>
                <Grid container direction="column" alignItems="center">
                    <Button
                        sx={{my: 0.5}}
                        variant="outlined"
                        size="small"
                        onClick={handleAllRight}
                        disabled={excluded.length === 0}
                        aria-label="move all included"
                    >
                        ≫
                    </Button>
                    <Button
                        sx={{my: 0.5}}
                        variant="outlined"
                        size="small"
                        onClick={handleCheckedRight}
                        disabled={excludedChecked.length === 0}
                        aria-label="move selected included"
                    >
                        &gt;
                    </Button>
                    <Button
                        sx={{my: 0.5}}
                        variant="outlined"
                        size="small"
                        onClick={handleCheckedLeft}
                        disabled={includedChecked.length === 0}
                        aria-label="move selected excluded"
                    >
                        &lt;
                    </Button>
                    <Button
                        sx={{my: 0.5}}
                        variant="outlined"
                        size="small"
                        onClick={handleAllLeft}
                        disabled={included?.length === 0}
                        aria-label="move all excluded"
                    >
                        ≪
                    </Button>
                </Grid>
            </Grid>
            <Grid item>
                <SearchPopupBar
                    sx={{
                        my:1
                    }}
                    items={included}
                    displayProperty={propertyToDisplay}
                    value={rightSearchValue}
                    setValue={setRightSearchValue}
                />
                <Grid container direction="column" alignItems="right">
                    {/*<SearchBar sx={{mb: 1}} items={included} setItems={setIncludedFiltered}/>*/}
                    {customList(rightSearchValue ? [rightSearchValue] : included, rightPage) /* includedFiltered*/}
                    <Pagination
                        sx={{ mt: 1}}
                        component="div"
                        count={getPageCount(included)} // includedFiltered
                        rowsperpage={rowsPerPage}
                        page={rightPage}
                        onChange={handleChangeRightPage}
                    />
                </Grid>
            </Grid>
        </Grid>
    );
}
TransferList.propTypes = {
    excluded: PropTypes.array,
    setExcluded: PropTypes.func.isRequired,
    included: PropTypes.array,
    setIncluded: PropTypes.func.isRequired,
    propertyToDisplay: PropTypes.string
};