import moment from 'moment'

/**
 * 判断是否为外链：https://
 * @param {string} path
 * @returns {Boolean}
 */
export function isExternal(path) {
  return /^(https?:|mailto:|tel:)/.test(path);
}


/**
 * 日期格式化
 * @param {*} date 
 * @param {*} format 
 * @returns 
 */
export function dateFormat(date,format = 'YYYY-MM-DD'){
  return moment(date).format(format)
}

