import {isNullOrEmpty} from "./ValidatorExtensions";


export function flatMapUnique(array, callback, cmpFunc) {
    if(isNullOrEmpty(...arguments)){
        return [];
    }

    const flatMap = array.flatMap(callback);

    return flatMap.filter((value, index, self) =>
            index === self.findIndex((t) => (
                cmpFunc(value, t)
            ))
    );
}