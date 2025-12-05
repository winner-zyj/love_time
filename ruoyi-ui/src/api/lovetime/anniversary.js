import request from '@/utils/request'

// 查询纪念日列表
export function listAnniversary(query) {
  return request({
    url: '/api/anniversary/list',
    method: 'get',
    params: query
  })
}

// 添加纪念日
export function addAnniversary(data) {
  return request({
    url: '/api/anniversary/create',
    method: 'post',
    data: data
  })
}

// 修改纪念日
export function updateAnniversary(id, data) {
  return request({
    url: '/api/anniversary/update/' + id,
    method: 'put',
    data: data
  })
}

// 删除纪念日
export function delAnniversary(id) {
  return request({
    url: '/api/anniversary/delete/' + id,
    method: 'delete'
  })
}

// 切换纪念日提醒状态
export function toggleRemind(id, data) {
  return request({
    url: '/api/anniversary/remind/' + id,
    method: 'put',
    data: data
  })
}