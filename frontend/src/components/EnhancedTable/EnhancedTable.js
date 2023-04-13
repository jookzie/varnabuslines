/*
*   Reference: https://mui.com/material-ui/react-table/
*/

import * as React from 'react';
import Box from '@mui/material/Box';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TablePagination from '@mui/material/TablePagination';
import TableRow from '@mui/material/TableRow';
import FormControlLabel from '@mui/material/FormControlLabel';
import Switch from '@mui/material/Switch';
import EnhancedTableToolbar from "./EnhancedTableToolbar";
import EnhancedTableHead from "./EnhancedTableHead";
import {IconButton} from "@mui/material";
import DeleteIcon from '@mui/icons-material/Delete';
import EditOutlinedIcon from '@mui/icons-material/EditOutlined';
import PropTypes from "prop-types";
import {toSentenceCase} from "../../services/utils/StringExtensions";


function descendingComparator(a, b, orderBy) {
    if (b[orderBy] < a[orderBy]) {
        return -1;
    }
    if (b[orderBy] > a[orderBy]) {
        return 1;
    }
    return 0;
}

function getComparator(order, orderBy) {
    return order === 'desc'
        ? (a, b) => descendingComparator(a, b, orderBy)
        : (a, b) => -descendingComparator(a, b, orderBy);
}

// Rows are sorted by the first column in descending order.
// The first value of the rows MUST be a unique identifier, or else functionality will break.
export default function EnhancedTable(props) {

    const {
        entityName,
        rows,
        setCreateDialogState,
        setUpdateDialogState,
        setDeleteDialogState,
        setSelectedTarget,
        format,
        compactViewEnabled,
        title
    } = props;

    const [order, setOrder] = React.useState('asc');
    const [page, setPage] = React.useState(0);
    const [dense, setDense] = React.useState(!compactViewEnabled);
    const [rowsPerPage, setRowsPerPage] = React.useState(5);
    const [orderBy, setOrderBy] = React.useState('');

    const dataLabels = Object.keys(rows[0] || {});

    function getDefaultTitle(entityName) {
        return entityName
            ? (toSentenceCase(entityName) + (entityName.charAt(entityName.length - 1) === 's' ? 'es' : 's'))
            : 'Entities';
    }


    function handleRequestSort(event, property) {
        const isAsc = orderBy === property && order === 'asc';
        setOrder(isAsc ? 'desc' : 'asc');
        setOrderBy(property);
    }

    function handleChangePage (event, newPage) {
        setPage(newPage);
    }

    function handleChangeRowsPerPage(event) {
        setRowsPerPage(parseInt(event.target.value, 10));
        setPage(0);
    }

    function handleChangeDense(event) {
        setDense(event.target.checked);
    }

    // Avoid a layout jump when reaching the last page with empty rows.
    const emptyRows =
        page > 0 ? Math.max(0, (1 + page) * rowsPerPage - rows && rows.length) : 0;

    function handleCreate() {
        setCreateDialogState(true);
    }

    function handleEdit(row) {
        setSelectedTarget(row);
        setUpdateDialogState(true);
    }

    function handleDelete(row) {
        setSelectedTarget(row);
        setDeleteDialogState(true);
    }


    function defaultFormat(value) {
        switch(typeof value) {
            case value === null:
            case 'undefined':
                return '';
            case 'boolean':
                return value ? 'Yes' : 'No';
            case 'object':
                return defaultFormat(value.toString());
            case Array.isArray(value):
                return value.join(' \n');
            case 'string':
            case 'number':
            default:
                return value;
        }
    }

    return (
        <Box sx={{
            mx: 10, mt: 20, mb: 10, py: 1, px: 2,
            border: 2, borderRadius: 5,
            borderColor: 'primary.main',
            boxShadow: 4,
            width: 'auto'
        }}>
            <EnhancedTableToolbar
                title={title || getDefaultTitle(entityName)}
                onClickAddNew={setCreateDialogState ? handleCreate : null}
            />
            <TableContainer>
                <Table
                    sx={{minWidth: 750}}
                    aria-labelledby="tableTitle"
                    size={dense ? 'small' : 'medium'}
                >
                    <EnhancedTableHead
                        order={order}
                        orderBy={orderBy}
                        onRequestSort={handleRequestSort}
                        dataLabels={dataLabels}
                        actionColumnVisibility={setUpdateDialogState && setDeleteDialogState}
                    />
                    <TableBody>
                        {Array.isArray(rows) && rows
                            .sort(getComparator(order, orderBy))
                            .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                            .map((row) => {
                                const rowEnum = Object.keys(row).map((k) => row[k]);

                                return (
                                    <TableRow
                                        hover
                                        key={rowEnum[0]}
                                    >
                                        {rowEnum.map((value, index) =>
                                            (<TableCell align="left" key={`${rowEnum[0]}-${index}`}>
                                                {format ? format(value, dataLabels[index], row) : defaultFormat(value)}
                                            </TableCell>))}
                                        <TableCell align="right" key={`actions-${rowEnum[0]}`}>
                                            {setUpdateDialogState &&
                                                <IconButton onClick={() => handleEdit(row)} key={`button-edit-${rowEnum[0]}`}>
                                                    <EditOutlinedIcon sx={{'&:hover': {color: 'mediumseagreen',}}} key={`icon-edit-${rowEnum[0]}`}/>
                                                </IconButton>
                                            }
                                            {setDeleteDialogState &&
                                                <IconButton onClick={() => handleDelete(row)} key={`button-delete-${rowEnum[0]}`}>
                                                    <DeleteIcon sx={{'&:hover': {color: 'red',}}} key={`icon-delete-${rowEnum[0]}`}/>
                                                </IconButton>
                                            }
                                        </TableCell>
                                    </TableRow>
                                );
                            })}
                        {emptyRows > 0 && (
                            <TableRow style={{height: (dense ? 33 : 53) * emptyRows}}>
                                <TableCell colSpan={6}/>
                            </TableRow>
                        )}
                    </TableBody>
                </Table>
            </TableContainer>

            <Box sx={{
                display: 'flex',
                border: 1,
                borderRadius: 2,
                boxShadow: 2,
                mb: 1
            }}>
                <FormControlLabel
                    sx={{flex: 1, ml: 2,}}
                    control={<Switch checked={dense} onChange={handleChangeDense}/>}
                    label="Compact view"
                />
                <TablePagination
                    rowsPerPageOptions={[5, 10, 25]}
                    component="div"
                    count={rows?.length || 0}
                    rowsPerPage={rowsPerPage}
                    page={page}
                    onPageChange={handleChangePage}
                    onRowsPerPageChange={handleChangeRowsPerPage}
                />
            </Box>
        </Box>
    );
}
EnhancedTable.propTypes = {
    entityName: PropTypes.string,
    rows: PropTypes.array,
    setCreateDialogState: PropTypes.func,
    setUpdateDialogState: PropTypes.func,
    setDeleteDialogState: PropTypes.func,
    setSelectedTarget: PropTypes.func,
    format: PropTypes.func
}